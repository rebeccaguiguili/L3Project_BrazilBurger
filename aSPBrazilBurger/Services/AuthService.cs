using Microsoft.EntityFrameworkCore;
using aSPBrazilBurger.Data;
using aSPBrazilBurger.Models.Entities;
using aSPBrazilBurger.Models.ViewModels;
using aSPBrazilBurger.Models.Enums;

namespace aSPBrazilBurger.Services;

public class AuthService : IAuthService
{
    private readonly AppDbContext _context;

    public AuthService(AppDbContext context)
    {
        _context = context;
    }

    public async Task<bool> RegisterAsync(RegisterViewModel model)
    {
        // Vérifier si l'email existe déjà
        if (await _context.Utilisateurs.AnyAsync(u => u.Email == model.Email))
        {
            return false;
        }

        // Vérifier si le téléphone existe déjà
        if (await _context.Utilisateurs.AnyAsync(u => u.Telephone == model.Telephone))
        {
            return false;
        }

        // Créer l'utilisateur
        var utilisateur = new Utilisateur
        {
            Nom = model.Nom,
            Prenom = model.Prenom,
            Telephone = model.Telephone,
            Email = model.Email,
            MotDePasse = HashPassword(model.MotDePasse),
            Role = RoleUtilisateur.Client,
            EstActif = true,
            DateCreation = DateTime.UtcNow
        };

        _context.Utilisateurs.Add(utilisateur);
        await _context.SaveChangesAsync();

        // Créer l'enregistrement Client associé
        var client = new Client
        {
            UtilisateurId = utilisateur.Id,
            Adresse = model.Adresse
        };

        _context.Clients.Add(client);
        await _context.SaveChangesAsync();

        return true;
    }

    public async Task<Utilisateur?> LoginAsync(LoginViewModel model)
    {
        var utilisateur = await _context.Utilisateurs
            .Include(u => u.Client)
            .FirstOrDefaultAsync(u => u.Email == model.Email);

        if (utilisateur == null)
        {
            return null;
        }

        if (!utilisateur.EstActif)
        {
            return null;
        }

        if (!VerifyPassword(utilisateur.MotDePasse, model.MotDePasse))
        {
            return null;
        }

        return utilisateur;
    }

    public async Task<Utilisateur?> GetUserByIdAsync(int userId)
    {
        return await _context.Utilisateurs
            .Include(u => u.Client)
            .FirstOrDefaultAsync(u => u.Id == userId);
    }

    public async Task<Utilisateur?> GetUserByEmailAsync(string email)
    {
        return await _context.Utilisateurs
            .Include(u => u.Client)
            .FirstOrDefaultAsync(u => u.Email == email);
    }

    public string HashPassword(string password)
    {
        return BCrypt.Net.BCrypt.HashPassword(password);
    }

    public bool VerifyPassword(string hash, string password)
    {
        return BCrypt.Net.BCrypt.Verify(password, hash);
    }
}
