using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace aSPBrazilBurger.Models.Entities;

[Table("menu")]
public class Menu
{
    [Key]
    [Column("produit_id")]
    [ForeignKey("Produit")]
    public int ProduitId { get; set; }

    [Column("description")]
    public string? Description { get; set; }

    // Navigation properties
    public Produit Produit { get; set; } = null!;
    public ICollection<MenuComposition> Compositions { get; set; } = new List<MenuComposition>();
}
