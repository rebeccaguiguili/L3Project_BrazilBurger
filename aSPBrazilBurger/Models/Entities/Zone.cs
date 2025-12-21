using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace aSPBrazilBurger.Models.Entities;

[Table("zone")]
public class Zone
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Required]
    [MaxLength(100)]
    [Column("nom")]
    public string Nom { get; set; } = string.Empty;

    [Required]
    [Column("prix_livraison", TypeName = "decimal(10,2)")]
    public decimal PrixLivraison { get; set; }
}
