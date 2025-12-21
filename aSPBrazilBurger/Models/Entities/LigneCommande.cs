using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace aSPBrazilBurger.Models.Entities;

[Table("ligne_commande")]
public class LigneCommande
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("commande_id")]
    [ForeignKey("Commande")]
    public int? CommandeId { get; set; }

    [Column("produit_id")]
    [ForeignKey("Produit")]
    public int? ProduitId { get; set; }

    [Required]
    [Column("quantite")]
    public int Quantite { get; set; }

    [Required]
    [Column("prix_unitaire", TypeName = "decimal(10,2)")]
    public decimal PrixUnitaire { get; set; }

    // Navigation properties
    public Commande? Commande { get; set; }
    public Produit? Produit { get; set; }
}
