using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace aSPBrazilBurger.Models.Entities;

[Table("client")]
public class Client
{
    [Key]
    [Column("utilisateur_id")]
    [ForeignKey("Utilisateur")]
    public int UtilisateurId { get; set; }

    [Column("adresse")]
    public string? Adresse { get; set; }

    // Navigation properties
    public Utilisateur Utilisateur { get; set; } = null!;
    public ICollection<Commande> Commandes { get; set; } = new List<Commande>();
}
