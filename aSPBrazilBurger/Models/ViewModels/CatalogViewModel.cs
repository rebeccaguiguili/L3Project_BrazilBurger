using aSPBrazilBurger.Models.Entities;

namespace aSPBrazilBurger.Models.ViewModels;

public class CatalogViewModel
{
    public List<Burger> Burgers { get; set; } = new List<Burger>();
    public List<Menu> Menus { get; set; } = new List<Menu>();
    public string? CurrentFilter { get; set; }
}
