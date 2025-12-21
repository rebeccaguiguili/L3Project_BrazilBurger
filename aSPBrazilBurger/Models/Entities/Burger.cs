using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace aSPBrazilBurger.Models.Entities;

[Table("burger")]
public class Burger
{
    [Key]
    [Column("produit_id")]
    [ForeignKey("Produit")]
    public int ProduitId { get; set; }

    [Column("description")]
    public string? Description { get; set; }

    // Navigation properties
    public Produit Produit { get; set; } = null!;
    public ICollection<MenuComposition> MenuCompositions { get; set; } = new List<MenuComposition>();
}
