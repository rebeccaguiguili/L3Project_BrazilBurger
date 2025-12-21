using aSPBrazilBurger.Models.Entities;

namespace aSPBrazilBurger.Models.ViewModels;

public class ProductDetailViewModel
{
    public int ProduitId { get; set; }
    public string Nom { get; set; } = string.Empty;
    public string? Description { get; set; }
    public decimal Prix { get; set; }
    public string? ImageUrl { get; set; }
    public string TypeProduit { get; set; } = string.Empty; // "Burger", "Menu", "Complement"

    // Pour les menus : composition
    public List<MenuCompositionDetail>? Composition { get; set; }
}

public class MenuCompositionDetail
{
    public string NomBurger { get; set; } = string.Empty;
    public string NomComplement { get; set; } = string.Empty;
    public string TypeComplement { get; set; } = string.Empty;
}
