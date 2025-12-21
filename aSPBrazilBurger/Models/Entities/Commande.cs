using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using aSPBrazilBurger.Models.Enums;

namespace aSPBrazilBurger.Models.Entities;

[Table("commande")]
public class Commande
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("client_id")]
    [ForeignKey("Client")]
    public int? ClientId { get; set; }

    [Column("date_commande")]
    public DateTime DateCommande { get; set; } = DateTime.UtcNow;

    [Column("etat")]
    public EtatCommande Etat { get; set; } = EtatCommande.EnAttente;

    [Required]
    [Column("type_consommation")]
    public TypeConsommation TypeConsommation { get; set; }

    [Column("adresse_livraison")]
    public string? AdresseLivraison { get; set; }

    [Column("montant_total", TypeName = "decimal(10,2)")]
    public decimal MontantTotal { get; set; } = 0;

    // Navigation properties
    public Client? Client { get; set; }
    public ICollection<LigneCommande> LignesCommande { get; set; } = new List<LigneCommande>();
    public ICollection<Paiement> Paiements { get; set; } = new List<Paiement>();
}
