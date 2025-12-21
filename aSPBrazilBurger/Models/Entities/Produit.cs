using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace aSPBrazilBurger.Models.Entities;

[Table("produit")]
public class Produit
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Required]
    [MaxLength(100)]
    [Column("nom")]
    public string Nom { get; set; } = string.Empty;

    [Required]
    [Column("prix", TypeName = "decimal(10,2)")]
    public decimal Prix { get; set; }

    [MaxLength(255)]
    [Column("image_url")]
    public string? ImageUrl { get; set; }

    [Column("est_archive")]
    public bool EstArchive { get; set; } = false;

    // Navigation properties
    public Burger? Burger { get; set; }
    public Menu? Menu { get; set; }
    public Complement? Complement { get; set; }
}
