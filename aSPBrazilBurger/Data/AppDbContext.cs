using Microsoft.EntityFrameworkCore;
using aSPBrazilBurger.Models.Entities;
using aSPBrazilBurger.Models.Enums;

namespace aSPBrazilBurger.Data;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
    }

    // DbSets pour toutes les entités
    public DbSet<Utilisateur> Utilisateurs { get; set; }
    public DbSet<Client> Clients { get; set; }
    public DbSet<Produit> Produits { get; set; }
    public DbSet<Burger> Burgers { get; set; }
    public DbSet<Menu> Menus { get; set; }
    public DbSet<Complement> Complements { get; set; }
    public DbSet<MenuComposition> MenuCompositions { get; set; }
    public DbSet<Commande> Commandes { get; set; }
    public DbSet<LigneCommande> LignesCommande { get; set; }
    public DbSet<Paiement> Paiements { get; set; }
    public DbSet<Zone> Zones { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        // Configuration des enums PostgreSQL
        modelBuilder.HasPostgresEnum<RoleUtilisateur>();
        modelBuilder.HasPostgresEnum<EtatCommande>();
        modelBuilder.HasPostgresEnum<TypeConsommation>();
        modelBuilder.HasPostgresEnum<MethodePaiement>();
        modelBuilder.HasPostgresEnum<StatutLivraison>();
        modelBuilder.HasPostgresEnum<TypeComplement>();

        // Configuration de la clé composite pour MenuComposition
        modelBuilder.Entity<MenuComposition>()
            .HasKey(mc => new { mc.MenuId, mc.BurgerId, mc.ComplementId });

        // Configuration des relations

        // Utilisateur -> Client (1:1)
        modelBuilder.Entity<Client>()
            .HasOne(c => c.Utilisateur)
            .WithOne(u => u.Client)
            .HasForeignKey<Client>(c => c.UtilisateurId)
            .OnDelete(DeleteBehavior.Cascade);

        // Produit -> Burger/Menu/Complement (1:1)
        modelBuilder.Entity<Burger>()
            .HasOne(b => b.Produit)
            .WithOne(p => p.Burger)
            .HasForeignKey<Burger>(b => b.ProduitId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<Menu>()
            .HasOne(m => m.Produit)
            .WithOne(p => p.Menu)
            .HasForeignKey<Menu>(m => m.ProduitId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<Complement>()
            .HasOne(c => c.Produit)
            .WithOne(p => p.Complement)
            .HasForeignKey<Complement>(c => c.ProduitId)
            .OnDelete(DeleteBehavior.Cascade);

        // MenuComposition -> Menu, Burger, Complement
        modelBuilder.Entity<MenuComposition>()
            .HasOne(mc => mc.Menu)
            .WithMany(m => m.Compositions)
            .HasForeignKey(mc => mc.MenuId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<MenuComposition>()
            .HasOne(mc => mc.Burger)
            .WithMany(b => b.MenuCompositions)
            .HasForeignKey(mc => mc.BurgerId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<MenuComposition>()
            .HasOne(mc => mc.Complement)
            .WithMany(c => c.MenuCompositions)
            .HasForeignKey(mc => mc.ComplementId)
            .OnDelete(DeleteBehavior.Cascade);

        // Client -> Commandes (1:N)
        modelBuilder.Entity<Commande>()
            .HasOne(c => c.Client)
            .WithMany(cl => cl.Commandes)
            .HasForeignKey(c => c.ClientId)
            .OnDelete(DeleteBehavior.SetNull);

        // Commande -> LignesCommande (1:N)
        modelBuilder.Entity<LigneCommande>()
            .HasOne(lc => lc.Commande)
            .WithMany(c => c.LignesCommande)
            .HasForeignKey(lc => lc.CommandeId)
            .OnDelete(DeleteBehavior.Cascade);

        // LigneCommande -> Produit (N:1)
        modelBuilder.Entity<LigneCommande>()
            .HasOne(lc => lc.Produit)
            .WithMany()
            .HasForeignKey(lc => lc.ProduitId)
            .OnDelete(DeleteBehavior.SetNull);

        // Commande -> Paiements (1:N)
        modelBuilder.Entity<Paiement>()
            .HasOne(p => p.Commande)
            .WithMany(c => c.Paiements)
            .HasForeignKey(p => p.CommandeId)
            .OnDelete(DeleteBehavior.Cascade);

        // Mapping des noms d'enums PostgreSQL (snake_case)
        modelBuilder.Entity<Utilisateur>()
            .Property(u => u.Role)
            .HasConversion<string>()
            .HasColumnType("role_utilisateur");

        modelBuilder.Entity<Commande>()
            .Property(c => c.Etat)
            .HasConversion<string>()
            .HasColumnType("etat_commande");

        modelBuilder.Entity<Commande>()
            .Property(c => c.TypeConsommation)
            .HasConversion<string>()
            .HasColumnType("type_consommation");

        modelBuilder.Entity<Paiement>()
            .Property(p => p.Methode)
            .HasConversion<string>()
            .HasColumnType("methode_paiement");

        modelBuilder.Entity<Complement>()
            .Property(c => c.Type)
            .HasConversion<string>()
            .HasColumnType("type_complement");
    }

    protected override void ConfigureConventions(ModelConfigurationBuilder configurationBuilder)
    {
        // Mapping automatique des enums vers snake_case
        configurationBuilder.Properties<RoleUtilisateur>()
            .HaveConversion<string>();

        configurationBuilder.Properties<EtatCommande>()
            .HaveConversion<string>();

        configurationBuilder.Properties<TypeConsommation>()
            .HaveConversion<string>();

        configurationBuilder.Properties<MethodePaiement>()
            .HaveConversion<string>();

        configurationBuilder.Properties<StatutLivraison>()
            .HaveConversion<string>();

        configurationBuilder.Properties<TypeComplement>()
            .HaveConversion<string>();
    }
}
