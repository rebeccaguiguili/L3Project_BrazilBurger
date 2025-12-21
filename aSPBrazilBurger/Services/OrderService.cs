using Microsoft.EntityFrameworkCore;
using aSPBrazilBurger.Data;
using aSPBrazilBurger.Models.Entities;
using aSPBrazilBurger.Models.ViewModels;
using aSPBrazilBurger.Models.Enums;

namespace aSPBrazilBurger.Services;

public class OrderService : IOrderService
{
    private readonly AppDbContext _context;
    private readonly IProductService _productService;

    public OrderService(AppDbContext context, IProductService productService)
    {
        _context = context;
        _productService = productService;
    }

    public async Task<int> CreateOrderAsync(CreateOrderViewModel model, int clientId)
    {
        // Créer la commande
        var commande = new Commande
        {
            ClientId = clientId,
            DateCommande = DateTime.UtcNow,
            Etat = EtatCommande.EnAttente,
            TypeConsommation = model.TypeConsommation,
            AdresseLivraison = model.AdresseLivraison,
            MontantTotal = 0
        };

        _context.Commandes.Add(commande);
        await _context.SaveChangesAsync();

        // Ajouter le produit principal à la commande
        var produit = await _productService.GetProduitByIdAsync(model.ProduitId);
        if (produit == null)
        {
            throw new Exception("Produit introuvable");
        }

        var prixProduit = produit.Prix;

        // Si c'est un menu, calculer le prix total
        if (produit.Menu != null)
        {
            prixProduit = await _productService.CalculateMenuPriceAsync(produit.Id);
        }

        var ligneProduit = new LigneCommande
        {
            CommandeId = commande.Id,
            ProduitId = produit.Id,
            Quantite = model.Quantite,
            PrixUnitaire = prixProduit
        };

        _context.LignesCommande.Add(ligneProduit);
        commande.MontantTotal += prixProduit * model.Quantite;

        // Ajouter les compléments sélectionnés
        if (model.ComplementIds != null && model.ComplementIds.Any())
        {
            foreach (var complementId in model.ComplementIds)
            {
                var complement = await _productService.GetProduitByIdAsync(complementId);
                if (complement != null && complement.Complement != null)
                {
                    var ligneComplement = new LigneCommande
                    {
                        CommandeId = commande.Id,
                        ProduitId = complement.Id,
                        Quantite = model.Quantite,
                        PrixUnitaire = complement.Prix
                    };

                    _context.LignesCommande.Add(ligneComplement);
                    commande.MontantTotal += complement.Prix * model.Quantite;
                }
            }
        }

        await _context.SaveChangesAsync();

        return commande.Id;
    }

    public async Task<bool> AddPaymentAsync(int commandeId, decimal montant, MethodePaiement methode)
    {
        var commande = await _context.Commandes.FindAsync(commandeId);
        if (commande == null)
        {
            return false;
        }

        // Créer le paiement
        var paiement = new Paiement
        {
            CommandeId = commandeId,
            DatePaiement = DateTime.UtcNow,
            Montant = montant,
            Methode = methode
        };

        _context.Paiements.Add(paiement);

        // Changer l'état de la commande à Validée
        commande.Etat = EtatCommande.Validee;

        await _context.SaveChangesAsync();

        return true;
    }

    public async Task<List<OrderSummaryViewModel>> GetUserOrdersAsync(int clientId)
    {
        var commandes = await _context.Commandes
            .Include(c => c.LignesCommande)
            .Include(c => c.Paiements)
            .Where(c => c.ClientId == clientId)
            .OrderByDescending(c => c.DateCommande)
            .ToListAsync();

        return commandes.Select(c => new OrderSummaryViewModel
        {
            CommandeId = c.Id,
            DateCommande = c.DateCommande,
            Etat = c.Etat,
            TypeConsommation = c.TypeConsommation,
            MontantTotal = c.MontantTotal,
            NombreProduits = c.LignesCommande.Sum(l => l.Quantite),
            EstPayee = c.Paiements.Any()
        }).ToList();
    }

    public async Task<OrderTrackingViewModel?> GetOrderDetailsAsync(int commandeId)
    {
        var commande = await _context.Commandes
            .Include(c => c.LignesCommande)
                .ThenInclude(l => l.Produit)
            .Include(c => c.Paiements)
            .FirstOrDefaultAsync(c => c.Id == commandeId);

        if (commande == null)
        {
            return null;
        }

        return new OrderTrackingViewModel
        {
            CommandeId = commande.Id,
            DateCommande = commande.DateCommande,
            Etat = commande.Etat,
            TypeConsommation = commande.TypeConsommation,
            AdresseLivraison = commande.AdresseLivraison,
            MontantTotal = commande.MontantTotal,
            Lignes = commande.LignesCommande.Select(l => new OrderLineViewModel
            {
                NomProduit = l.Produit?.Nom ?? "Produit inconnu",
                Quantite = l.Quantite,
                PrixUnitaire = l.PrixUnitaire,
                SousTotal = l.PrixUnitaire * l.Quantite
            }).ToList(),
            Paiements = commande.Paiements.Select(p => new PaymentViewModel
            {
                DatePaiement = p.DatePaiement,
                Montant = p.Montant,
                Methode = p.Methode
            }).ToList()
        };
    }

    public async Task<bool> UpdateOrderStatusAsync(int commandeId, EtatCommande nouvelEtat)
    {
        var commande = await _context.Commandes.FindAsync(commandeId);
        if (commande == null)
        {
            return false;
        }

        commande.Etat = nouvelEtat;
        await _context.SaveChangesAsync();

        return true;
    }
}
