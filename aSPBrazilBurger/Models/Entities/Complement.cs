using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using aSPBrazilBurger.Models.Enums;

namespace aSPBrazilBurger.Models.Entities;

[Table("complement")]
public class Complement
{
    [Key]
    [Column("produit_id")]
    [ForeignKey("Produit")]
    public int ProduitId { get; set; }

    [Required]
    [Column("type")]
    public TypeComplement Type { get; set; }

    // Navigation properties
    public Produit Produit { get; set; } = null!;
    public ICollection<MenuComposition> MenuCompositions { get; set; } = new List<MenuComposition>();
}
