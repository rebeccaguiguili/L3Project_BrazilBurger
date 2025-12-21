using Microsoft.AspNetCore.Mvc;
using aSPBrazilBurger.Services;
using aSPBrazilBurger.Models.ViewModels;

namespace aSPBrazilBurger.Controllers;

public class CatalogController : Controller
{
    private readonly IProductService _productService;

    public CatalogController(IProductService productService)
    {
        _productService = productService;
    }

    [HttpGet]
    public async Task<IActionResult> Index(string? filter)
    {
        var viewModel = new CatalogViewModel
        {
            CurrentFilter = filter
        };

        if (string.IsNullOrEmpty(filter) || filter.Equals("tous", StringComparison.OrdinalIgnoreCase))
        {
            // Afficher tous les produits
            viewModel.Burgers = await _productService.GetBurgersAsync();
            viewModel.Menus = await _productService.GetMenusAsync();
        }
        else if (filter.Equals("burgers", StringComparison.OrdinalIgnoreCase))
        {
            // Afficher uniquement les burgers
            viewModel.Burgers = await _productService.GetBurgersAsync();
        }
        else if (filter.Equals("menus", StringComparison.OrdinalIgnoreCase))
        {
            // Afficher uniquement les menus
            viewModel.Menus = await _productService.GetMenusAsync();
        }
        else
        {
            // Filtre invalide, afficher tout
            viewModel.Burgers = await _productService.GetBurgersAsync();
            viewModel.Menus = await _productService.GetMenusAsync();
        }

        return View(viewModel);
    }

    [HttpGet]
    public async Task<IActionResult> Details(int id)
    {
        var productDetail = await _productService.GetProductDetailAsync(id);

        if (productDetail == null)
        {
            TempData["ErrorMessage"] = "Produit introuvable.";
            return RedirectToAction(nameof(Index));
        }

        return View(productDetail);
    }
}
