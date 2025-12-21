using aSPBrazilBurger.Models.Enums;

namespace aSPBrazilBurger.Models.ViewModels;

public class OrderTrackingViewModel
{
    public int CommandeId { get; set; }
    public DateTime DateCommande { get; set; }
    public EtatCommande Etat { get; set; }
    public TypeConsommation TypeConsommation { get; set; }
    public string? AdresseLivraison { get; set; }
    public decimal MontantTotal { get; set; }

    public List<OrderLineViewModel> Lignes { get; set; } = new List<OrderLineViewModel>();
    public List<PaymentViewModel> Paiements { get; set; } = new List<PaymentViewModel>();
}

public class OrderLineViewModel
{
    public string NomProduit { get; set; } = string.Empty;
    public int Quantite { get; set; }
    public decimal PrixUnitaire { get; set; }
    public decimal SousTotal { get; set; }
}

public class PaymentViewModel
{
    public DateTime DatePaiement { get; set; }
    public decimal Montant { get; set; }
    public MethodePaiement Methode { get; set; }
}
