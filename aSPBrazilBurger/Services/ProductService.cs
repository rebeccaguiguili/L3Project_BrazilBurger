using Microsoft.EntityFrameworkCore;
using aSPBrazilBurger.Data;
using aSPBrazilBurger.Models.Entities;
using aSPBrazilBurger.Models.ViewModels;

namespace aSPBrazilBurger.Services;

public class ProductService : IProductService
{
    private readonly AppDbContext _context;

    public ProductService(AppDbContext context)
    {
        _context = context;
    }

    public async Task<List<Burger>> GetBurgersAsync(bool activeOnly = true)
    {
        var query = _context.Burgers
            .Include(b => b.Produit)
            .AsQueryable();

        if (activeOnly)
        {
            query = query.Where(b => !b.Produit.EstArchive);
        }

        return await query.ToListAsync();
    }

    public async Task<List<Menu>> GetMenusAsync(bool activeOnly = true)
    {
        var query = _context.Menus
            .Include(m => m.Produit)
            .Include(m => m.Compositions)
                .ThenInclude(c => c.Burger)
                    .ThenInclude(b => b.Produit)
            .Include(m => m.Compositions)
                .ThenInclude(c => c.Complement)
                    .ThenInclude(comp => comp.Produit)
            .AsQueryable();

        if (activeOnly)
        {
            query = query.Where(m => !m.Produit.EstArchive);
        }

        return await query.ToListAsync();
    }

    public async Task<List<Complement>> GetComplementsAsync(bool activeOnly = true)
    {
        var query = _context.Complements
            .Include(c => c.Produit)
            .AsQueryable();

        if (activeOnly)
        {
            query = query.Where(c => !c.Produit.EstArchive);
        }

        return await query.ToListAsync();
    }

    public async Task<ProductDetailViewModel?> GetProductDetailAsync(int produitId)
    {
        var produit = await _context.Produits.FindAsync(produitId);
        if (produit == null || produit.EstArchive)
        {
            return null;
        }

        // Vérifier si c'est un burger
        var burger = await _context.Burgers
            .Include(b => b.Produit)
            .FirstOrDefaultAsync(b => b.ProduitId == produitId);

        if (burger != null)
        {
            return new ProductDetailViewModel
            {
                ProduitId = produit.Id,
                Nom = produit.Nom,
                Description = burger.Description,
                Prix = produit.Prix,
                ImageUrl = produit.ImageUrl,
                TypeProduit = "Burger"
            };
        }

        // Vérifier si c'est un menu
        var menu = await _context.Menus
            .Include(m => m.Produit)
            .Include(m => m.Compositions)
                .ThenInclude(c => c.Burger)
                    .ThenInclude(b => b.Produit)
            .Include(m => m.Compositions)
                .ThenInclude(c => c.Complement)
                    .ThenInclude(comp => comp.Produit)
            .FirstOrDefaultAsync(m => m.ProduitId == produitId);

        if (menu != null)
        {
            var composition = menu.Compositions.Select(c => new MenuCompositionDetail
            {
                NomBurger = c.Burger.Produit.Nom,
                NomComplement = c.Complement.Produit.Nom,
                TypeComplement = c.Complement.Type.ToString()
            }).ToList();

            var prix = await CalculateMenuPriceAsync(produitId);

            return new ProductDetailViewModel
            {
                ProduitId = produit.Id,
                Nom = produit.Nom,
                Description = menu.Description,
                Prix = prix,
                ImageUrl = produit.ImageUrl,
                TypeProduit = "Menu",
                Composition = composition
            };
        }

        // Vérifier si c'est un complément
        var complement = await _context.Complements
            .Include(c => c.Produit)
            .FirstOrDefaultAsync(c => c.ProduitId == produitId);

        if (complement != null)
        {
            return new ProductDetailViewModel
            {
                ProduitId = produit.Id,
                Nom = produit.Nom,
                Description = complement.Type.ToString(),
                Prix = produit.Prix,
                ImageUrl = produit.ImageUrl,
                TypeProduit = "Complement"
            };
        }

        return null;
    }

    public async Task<decimal> CalculateMenuPriceAsync(int menuId)
    {
        var menu = await _context.Menus
            .Include(m => m.Compositions)
                .ThenInclude(c => c.Burger)
                    .ThenInclude(b => b.Produit)
            .Include(m => m.Compositions)
                .ThenInclude(c => c.Complement)
                    .ThenInclude(comp => comp.Produit)
            .FirstOrDefaultAsync(m => m.ProduitId == menuId);

        if (menu == null)
        {
            return 0;
        }

        decimal totalPrice = 0;

        foreach (var composition in menu.Compositions)
        {
            totalPrice += composition.Burger.Produit.Prix;
            totalPrice += composition.Complement.Produit.Prix;
        }

        return totalPrice;
    }

    public async Task<Produit?> GetProduitByIdAsync(int produitId)
    {
        return await _context.Produits
            .Include(p => p.Burger)
            .Include(p => p.Menu)
            .Include(p => p.Complement)
            .FirstOrDefaultAsync(p => p.Id == produitId);
    }
}
