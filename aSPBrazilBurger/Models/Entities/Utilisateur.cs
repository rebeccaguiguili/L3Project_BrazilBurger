using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using aSPBrazilBurger.Models.Enums;

namespace aSPBrazilBurger.Models.Entities;

[Table("utilisateur")]
public class Utilisateur
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Required]
    [MaxLength(50)]
    [Column("nom")]
    public string Nom { get; set; } = string.Empty;

    [Required]
    [MaxLength(50)]
    [Column("prenom")]
    public string Prenom { get; set; } = string.Empty;

    [Required]
    [MaxLength(20)]
    [Column("telephone")]
    public string Telephone { get; set; } = string.Empty;

    [Required]
    [MaxLength(100)]
    [Column("email")]
    public string Email { get; set; } = string.Empty;

    [Required]
    [MaxLength(255)]
    [Column("mot_de_passe")]
    public string MotDePasse { get; set; } = string.Empty;

    [Required]
    [Column("role")]
    public RoleUtilisateur Role { get; set; }

    [Column("est_actif")]
    public bool EstActif { get; set; } = true;

    [Column("date_creation")]
    public DateTime DateCreation { get; set; } = DateTime.UtcNow;

    // Navigation properties
    public Client? Client { get; set; }
}
