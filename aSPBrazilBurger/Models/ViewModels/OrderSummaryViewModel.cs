using aSPBrazilBurger.Models.Enums;

namespace aSPBrazilBurger.Models.ViewModels;

public class OrderSummaryViewModel
{
    public int CommandeId { get; set; }
    public DateTime DateCommande { get; set; }
    public EtatCommande Etat { get; set; }
    public TypeConsommation TypeConsommation { get; set; }
    public decimal MontantTotal { get; set; }
    public int NombreProduits { get; set; }
    public bool EstPayee { get; set; }
}
