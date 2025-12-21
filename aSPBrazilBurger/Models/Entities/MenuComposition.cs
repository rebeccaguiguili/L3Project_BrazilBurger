using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace aSPBrazilBurger.Models.Entities;

[Table("menu_composition")]
public class MenuComposition
{
    [Column("menu_id")]
    [ForeignKey("Menu")]
    public int MenuId { get; set; }

    [Column("burger_id")]
    [ForeignKey("Burger")]
    public int BurgerId { get; set; }

    [Column("complement_id")]
    [ForeignKey("Complement")]
    public int ComplementId { get; set; }

    // Navigation properties
    public Menu Menu { get; set; } = null!;
    public Burger Burger { get; set; } = null!;
    public Complement Complement { get; set; } = null!;
}
