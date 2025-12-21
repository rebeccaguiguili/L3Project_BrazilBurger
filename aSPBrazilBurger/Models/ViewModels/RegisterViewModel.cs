using System.ComponentModel.DataAnnotations;

namespace aSPBrazilBurger.Models.ViewModels;

public class RegisterViewModel
{
    [Required(ErrorMessage = "Le nom est requis")]
    [StringLength(50, ErrorMessage = "Le nom ne peut pas dépasser 50 caractères")]
    [Display(Name = "Nom")]
    public string Nom { get; set; } = string.Empty;

    [Required(ErrorMessage = "Le prénom est requis")]
    [StringLength(50, ErrorMessage = "Le prénom ne peut pas dépasser 50 caractères")]
    [Display(Name = "Prénom")]
    public string Prenom { get; set; } = string.Empty;

    [Required(ErrorMessage = "Le téléphone est requis")]
    [Phone(ErrorMessage = "Format de téléphone invalide")]
    [StringLength(20, ErrorMessage = "Le téléphone ne peut pas dépasser 20 caractères")]
    [Display(Name = "Téléphone")]
    public string Telephone { get; set; } = string.Empty;

    [Required(ErrorMessage = "L'email est requis")]
    [EmailAddress(ErrorMessage = "Format d'email invalide")]
    [StringLength(100, ErrorMessage = "L'email ne peut pas dépasser 100 caractères")]
    [Display(Name = "Email")]
    public string Email { get; set; } = string.Empty;

    [Required(ErrorMessage = "Le mot de passe est requis")]
    [StringLength(100, MinimumLength = 6, ErrorMessage = "Le mot de passe doit contenir au moins 6 caractères")]
    [DataType(DataType.Password)]
    [Display(Name = "Mot de passe")]
    public string MotDePasse { get; set; } = string.Empty;

    [Required(ErrorMessage = "La confirmation est requise")]
    [DataType(DataType.Password)]
    [Compare("MotDePasse", ErrorMessage = "Les mots de passe ne correspondent pas")]
    [Display(Name = "Confirmer le mot de passe")]
    public string ConfirmerMotDePasse { get; set; } = string.Empty;

    [StringLength(255, ErrorMessage = "L'adresse ne peut pas dépasser 255 caractères")]
    [Display(Name = "Adresse")]
    public string? Adresse { get; set; }
}
