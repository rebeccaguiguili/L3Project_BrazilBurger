using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using aSPBrazilBurger.Models.Enums;

namespace aSPBrazilBurger.Models.Entities;

[Table("paiement")]
public class Paiement
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("commande_id")]
    [ForeignKey("Commande")]
    public int? CommandeId { get; set; }

    [Column("date_paiement")]
    public DateTime DatePaiement { get; set; } = DateTime.UtcNow;

    [Required]
    [Column("montant", TypeName = "decimal(10,2)")]
    public decimal Montant { get; set; }

    [Required]
    [Column("methode")]
    public MethodePaiement Methode { get; set; }

    // Navigation properties
    public Commande? Commande { get; set; }
}
