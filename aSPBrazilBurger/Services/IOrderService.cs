using aSPBrazilBurger.Models.ViewModels;
using aSPBrazilBurger.Models.Enums;

namespace aSPBrazilBurger.Services;

public interface IOrderService
{
    Task<int> CreateOrderAsync(CreateOrderViewModel model, int clientId);
    Task<bool> AddPaymentAsync(int commandeId, decimal montant, MethodePaiement methode);
    Task<List<OrderSummaryViewModel>> GetUserOrdersAsync(int clientId);
    Task<OrderTrackingViewModel?> GetOrderDetailsAsync(int commandeId);
    Task<bool> UpdateOrderStatusAsync(int commandeId, EtatCommande nouvelEtat);
}
