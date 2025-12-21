using System.ComponentModel.DataAnnotations;
using aSPBrazilBurger.Models.Enums;

namespace aSPBrazilBurger.Models.ViewModels;

public class CreateOrderViewModel
{
    [Required]
    public int ProduitId { get; set; }

    [Required]
    [Range(1, 100, ErrorMessage = "La quantité doit être entre 1 et 100")]
    public int Quantite { get; set; } = 1;

    [Required]
    public TypeConsommation TypeConsommation { get; set; }

    public string? AdresseLivraison { get; set; }

    // Liste des IDs de compléments sélectionnés
    public List<int> ComplementIds { get; set; } = new List<int>();

    // Pour affichage
    public string? NomProduit { get; set; }
    public decimal? PrixProduit { get; set; }
}
