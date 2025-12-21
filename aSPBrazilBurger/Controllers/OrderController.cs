using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using System.Security.Claims;
using aSPBrazilBurger.Services;
using aSPBrazilBurger.Models.ViewModels;
using aSPBrazilBurger.Models.Enums;

namespace aSPBrazilBurger.Controllers;

[Authorize]
public class OrderController : Controller
{
    private readonly IOrderService _orderService;
    private readonly IProductService _productService;

    public OrderController(IOrderService orderService, IProductService productService)
    {
        _orderService = orderService;
        _productService = productService;
    }

    [HttpGet]
    public async Task<IActionResult> Create(int productId)
    {
        var produit = await _productService.GetProduitByIdAsync(productId);
        if (produit == null)
        {
            TempData["ErrorMessage"] = "Produit introuvable.";
            return RedirectToAction("Index", "Catalog");
        }

        var prix = produit.Prix;
        if (produit.Menu != null)
        {
            prix = await _productService.CalculateMenuPriceAsync(productId);
        }

        var model = new CreateOrderViewModel
        {
            ProduitId = productId,
            NomProduit = produit.Nom,
            PrixProduit = prix,
            Quantite = 1,
            TypeConsommation = TypeConsommation.SurPlace
        };

        // Charger les compléments disponibles
        ViewBag.Complements = await _productService.GetComplementsAsync();

        return View(model);
    }

    [HttpPost]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> Create(CreateOrderViewModel model)
    {
        if (!ModelState.IsValid)
        {
            ViewBag.Complements = await _productService.GetComplementsAsync();
            return View(model);
        }

        // Valider l'adresse de livraison si nécessaire
        if (model.TypeConsommation == TypeConsommation.Livraison && string.IsNullOrWhiteSpace(model.AdresseLivraison))
        {
            ModelState.AddModelError(nameof(model.AdresseLivraison), "L'adresse de livraison est requise.");
            ViewBag.Complements = await _productService.GetComplementsAsync();
            return View(model);
        }

        // Récupérer l'ID du client depuis les claims
        var clientIdClaim = User.FindFirst("ClientId")?.Value;
        if (string.IsNullOrEmpty(clientIdClaim) || !int.TryParse(clientIdClaim, out int clientId))
        {
            TempData["ErrorMessage"] = "Erreur d'authentification. Veuillez vous reconnecter.";
            return RedirectToAction("Login", "Account");
        }

        try
        {
            var commandeId = await _orderService.CreateOrderAsync(model, clientId);
            TempData["CommandeId"] = commandeId;
            return RedirectToAction(nameof(Payment), new { id = commandeId });
        }
        catch (Exception ex)
        {
            ModelState.AddModelError(string.Empty, $"Erreur lors de la création de la commande : {ex.Message}");
            ViewBag.Complements = await _productService.GetComplementsAsync();
            return View(model);
        }
    }

    [HttpGet]
    public async Task<IActionResult> Payment(int id)
    {
        var commande = await _orderService.GetOrderDetailsAsync(id);
        if (commande == null)
        {
            TempData["ErrorMessage"] = "Commande introuvable.";
            return RedirectToAction(nameof(MyOrders));
        }

        // Vérifier que la commande appartient à l'utilisateur
        var clientIdClaim = User.FindFirst("ClientId")?.Value;
        if (string.IsNullOrEmpty(clientIdClaim))
        {
            return Forbid();
        }

        return View(commande);
    }

    [HttpPost]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> ProcessPayment(int commandeId, string methode)
    {
        var commande = await _orderService.GetOrderDetailsAsync(commandeId);
        if (commande == null)
        {
            TempData["ErrorMessage"] = "Commande introuvable.";
            return RedirectToAction(nameof(MyOrders));
        }

        // Parser la méthode de paiement
        if (!Enum.TryParse<MethodePaiement>(methode, true, out var methodePaiement))
        {
            TempData["ErrorMessage"] = "Méthode de paiement invalide.";
            return RedirectToAction(nameof(Payment), new { id = commandeId });
        }

        // Enregistrer le paiement (simulation)
        var success = await _orderService.AddPaymentAsync(commandeId, commande.MontantTotal, methodePaiement);

        if (success)
        {
            TempData["SuccessMessage"] = $"Paiement de {commande.MontantTotal:N0} FCFA effectué avec succès via {methodePaiement} !";
            return RedirectToAction(nameof(Confirmation), new { id = commandeId });
        }
        else
        {
            TempData["ErrorMessage"] = "Erreur lors du traitement du paiement.";
            return RedirectToAction(nameof(Payment), new { id = commandeId });
        }
    }

    [HttpGet]
    public async Task<IActionResult> Confirmation(int id)
    {
        var commande = await _orderService.GetOrderDetailsAsync(id);
        if (commande == null)
        {
            TempData["ErrorMessage"] = "Commande introuvable.";
            return RedirectToAction(nameof(MyOrders));
        }

        return View(commande);
    }

    [HttpGet]
    public async Task<IActionResult> MyOrders()
    {
        var clientIdClaim = User.FindFirst("ClientId")?.Value;
        if (string.IsNullOrEmpty(clientIdClaim) || !int.TryParse(clientIdClaim, out int clientId))
        {
            TempData["ErrorMessage"] = "Erreur d'authentification.";
            return RedirectToAction("Login", "Account");
        }

        var commandes = await _orderService.GetUserOrdersAsync(clientId);
        return View(commandes);
    }

    [HttpGet]
    public async Task<IActionResult> Details(int id)
    {
        var commande = await _orderService.GetOrderDetailsAsync(id);
        if (commande == null)
        {
            TempData["ErrorMessage"] = "Commande introuvable.";
            return RedirectToAction(nameof(MyOrders));
        }

        return View(commande);
    }
}
