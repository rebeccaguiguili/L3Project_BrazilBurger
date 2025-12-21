using aSPBrazilBurger.Models.Entities;
using aSPBrazilBurger.Models.ViewModels;

namespace aSPBrazilBurger.Services;

public interface IProductService
{
    Task<List<Burger>> GetBurgersAsync(bool activeOnly = true);
    Task<List<Menu>> GetMenusAsync(bool activeOnly = true);
    Task<List<Complement>> GetComplementsAsync(bool activeOnly = true);
    Task<ProductDetailViewModel?> GetProductDetailAsync(int produitId);
    Task<decimal> CalculateMenuPriceAsync(int menuId);
    Task<Produit?> GetProduitByIdAsync(int produitId);
}
