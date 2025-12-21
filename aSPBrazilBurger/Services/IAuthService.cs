using aSPBrazilBurger.Models.Entities;
using aSPBrazilBurger.Models.ViewModels;

namespace aSPBrazilBurger.Services;

public interface IAuthService
{
    Task<bool> RegisterAsync(RegisterViewModel model);
    Task<Utilisateur?> LoginAsync(LoginViewModel model);
    Task<Utilisateur?> GetUserByIdAsync(int userId);
    Task<Utilisateur?> GetUserByEmailAsync(string email);
    string HashPassword(string password);
    bool VerifyPassword(string hash, string password);
}
