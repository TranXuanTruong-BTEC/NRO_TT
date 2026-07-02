/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package system;

import database.PlayerDAO;
import item.Item;
import item.Item.ItemOption;
import services.ItemService;
import player.Player;
import player.Service.InventoryService;

/**
 *
 * @author NROTIN
 */
public class QuaToriBot {

    public static void Qua_1(Player player) {
        Item ThoiVang = ItemService.gI().createNewItem((short) 457, 16);
        Item PhieuGiamGia = ItemService.gI().createNewItem((short) 459, 10);
        Item DaBaoVe = ItemService.gI().createNewItem((short) 987, 5);
        Item CaiTrang = ItemService.gI().createNewItem((short) 1732, 1); // 1732 Black Goku | 1731 Black Goku Rose
        Item PhuKien = ItemService.gI().createNewItem((short) 1721, 1); // Cá Koi Zombie 1721  | Cánh 1722
        Item Pet = ItemService.gI().createNewItem((short) 1654, 1);  // Chó 1654 | Capybara 1629
        Item Diem = ItemService.gI().createNewItem((short)1999,5);

// Option ThoiVang
        ThoiVang.itemOptions.add(new ItemOption(100, 1));

        // Option PhieuGiamGia
        PhieuGiamGia.itemOptions.add(new ItemOption(73, 1));

        // Option DaBaoVe
        DaBaoVe.itemOptions.add(new ItemOption(73, 1));

        // Option CaiTrang
        CaiTrang.itemOptions.add(new ItemOption(50, 25));
        CaiTrang.itemOptions.add(new ItemOption(103, 21));
        CaiTrang.itemOptions.add(new ItemOption(5, 11));
        CaiTrang.itemOptions.add(new ItemOption(14, 7));
        CaiTrang.itemOptions.add(new ItemOption(97, 30));
         CaiTrang.itemOptions.add(new ItemOption(5, 5));
         CaiTrang.itemOptions.add(new ItemOption(73, 30));

        // Option PhuKien
        PhuKien.itemOptions.add(new ItemOption(50, 5));
        PhuKien.itemOptions.add(new ItemOption(77, 5));
        PhuKien.itemOptions.add(new ItemOption(103, 5));
        PhuKien.itemOptions.add(new ItemOption(101, 5));

        // Option Pet
        Pet.itemOptions.add(new ItemOption(50, 5));
        Pet.itemOptions.add(new ItemOption(77, 5));
        Pet.itemOptions.add(new ItemOption(103, 5));
        Pet.itemOptions.add(new ItemOption(101, 5));
        
         // Option Diem
        Diem.itemOptions.add(new ItemOption(30,0));


        // Cập Nhật & Gửi Item Đến Balo Player
        InventoryService.gI().addItemBag(player, ThoiVang);
        InventoryService.gI().addItemBag(player, PhieuGiamGia);
        InventoryService.gI().addItemBag(player, DaBaoVe);
        InventoryService.gI().addItemBag(player, CaiTrang);
        InventoryService.gI().addItemBag(player, PhuKien);
        InventoryService.gI().addItemBag(player, Pet);
                InventoryService.gI().addItemBag(player, Diem);
                PlayerDAO.addDiemSuKien(player, 5);

        InventoryService.gI().sendItemBags(player);

    }

    public static void Qua_2(Player player) {
        Item ThoiVang = ItemService.gI().createNewItem((short) 457, 32);
        Item PhieuGiamGia = ItemService.gI().createNewItem((short) 459, 10);
        Item DaBaoVe = ItemService.gI().createNewItem((short) 987, 10);
        Item CaiTrang = ItemService.gI().createNewItem((short) 1732, 1); // 1732 Black Goku | 1731 Black Goku Rose
        Item PhuKien = ItemService.gI().createNewItem((short) 1721, 1); // Cá Koi Zombie 1721  | Cánh 1722
        Item Pet = ItemService.gI().createNewItem((short) 1654, 1);  // Chó 1654 | Capybara 1629
        Item TheTieuDoiTruongVang = ItemService.gI().createNewItem((short) 1204, 10); // Đội Trưởng Vàng 957 | Namek 1204
        Item Diem = ItemService.gI().createNewItem((short)1999,7);

        // Option ThoiVang
        ThoiVang.itemOptions.add(new ItemOption(100, 1));

        // Option PhieuGiamGia
        PhieuGiamGia.itemOptions.add(new ItemOption(73, 1));

        // Option DaBaoVe
        DaBaoVe.itemOptions.add(new ItemOption(73, 1));

        // Option CaiTrang
        CaiTrang.itemOptions.add(new ItemOption(50, 27));
        CaiTrang.itemOptions.add(new ItemOption(103, 24));
        CaiTrang.itemOptions.add(new ItemOption(5, 12));
        CaiTrang.itemOptions.add(new ItemOption(14, 9));
         CaiTrang.itemOptions.add(new ItemOption(5, 5));
         CaiTrang.itemOptions.add(new ItemOption(160, 100));

        // Option PhuKien
        PhuKien.itemOptions.add(new ItemOption(50, 6));
        PhuKien.itemOptions.add(new ItemOption(77, 6));
        PhuKien.itemOptions.add(new ItemOption(103, 6));
        PhuKien.itemOptions.add(new ItemOption(101, 6));

        // Option Pet
        Pet.itemOptions.add(new ItemOption(50, 6));
        Pet.itemOptions.add(new ItemOption(77, 6));
        Pet.itemOptions.add(new ItemOption(103, 6));
        Pet.itemOptions.add(new ItemOption(101, 6));

        // Option TheDoiTruongVang
        TheTieuDoiTruongVang.itemOptions.add(new ItemOption(73, 0));
        
         // Option Diem
        Diem.itemOptions.add(new ItemOption(30,0));

        // Cập Nhật & Gửi Item Đến Balo Player
        InventoryService.gI().addItemBag(player, ThoiVang);
        InventoryService.gI().addItemBag(player, PhieuGiamGia);
        InventoryService.gI().addItemBag(player, DaBaoVe);
        InventoryService.gI().addItemBag(player, CaiTrang);
        InventoryService.gI().addItemBag(player, PhuKien);
        InventoryService.gI().addItemBag(player, Pet);
        InventoryService.gI().addItemBag(player, TheTieuDoiTruongVang);
                InventoryService.gI().addItemBag(player, Diem);
                                PlayerDAO.addDiemSuKien(player, 7);


        InventoryService.gI().sendItemBags(player);
    }

    public static void Qua_3(Player player) {
        Item ThoiVang = ItemService.gI().createNewItem((short) 457, 64);
        Item PhieuGiamGia = ItemService.gI().createNewItem((short) 459, 10);
        Item DaBaoVe = ItemService.gI().createNewItem((short) 987, 20);
        Item CaiTrang = ItemService.gI().createNewItem((short) 1731, 1); // 1732 Black Goku | 1731 Black Goku Rose
        Item PhuKien = ItemService.gI().createNewItem((short) 1722, 1); // Cá Koi Zombie 1721  | Cánh 1722
        Item Pet = ItemService.gI().createNewItem((short) 1629, 1);  // Chó 1654 | Capybara 1629
        Item TheTieuDoiTruongVang = ItemService.gI().createNewItem((short) 956, 10); // Đội Trưởng Vàng 957 | Namek 1204
       Item CapsuleThanLinh = ItemService.gI().createNewItem((short) 1775, 2);
               Item Diem = ItemService.gI().createNewItem((short)1999,10);


        // Option ThoiVang
        ThoiVang.itemOptions.add(new ItemOption(100, 1));

        // Option PhieuGiamGia
        PhieuGiamGia.itemOptions.add(new ItemOption(73, 1));

        // Option DaBaoVe
        DaBaoVe.itemOptions.add(new ItemOption(73, 1));

        // Option CaiTrang
        CaiTrang.itemOptions.add(new ItemOption(50, 35));
        CaiTrang.itemOptions.add(new ItemOption(103, 27));
        CaiTrang.itemOptions.add(new ItemOption(5, 15));
        CaiTrang.itemOptions.add(new ItemOption(14, 11));
        CaiTrang.itemOptions.add(new ItemOption(93, 30));
        CaiTrang.itemOptions.add(new ItemOption(101, 100));

        // Option PhuKien
        PhuKien.itemOptions.add(new ItemOption(50, 7));
        PhuKien.itemOptions.add(new ItemOption(77, 7));
        PhuKien.itemOptions.add(new ItemOption(103, 7));
        PhuKien.itemOptions.add(new ItemOption(101, 7));

        // Option Pet
        Pet.itemOptions.add(new ItemOption(50, 7));
        Pet.itemOptions.add(new ItemOption(77, 7));
        Pet.itemOptions.add(new ItemOption(103, 7));
        Pet.itemOptions.add(new ItemOption(101, 7));

        // Option TheDoiTruongVang
        TheTieuDoiTruongVang.itemOptions.add(new ItemOption(73, 0));
        
         // Option Diem
        Diem.itemOptions.add(new ItemOption(30,0));

        // Cập Nhật & Gửi Item Đến Balo Player
        InventoryService.gI().addItemBag(player, ThoiVang);
        InventoryService.gI().addItemBag(player, PhieuGiamGia);
        InventoryService.gI().addItemBag(player, DaBaoVe);
        InventoryService.gI().addItemBag(player, CaiTrang);
        InventoryService.gI().addItemBag(player, PhuKien);
        InventoryService.gI().addItemBag(player, Pet);
        InventoryService.gI().addItemBag(player, TheTieuDoiTruongVang);
        InventoryService.gI().addItemBag(player, CapsuleThanLinh);
                InventoryService.gI().addItemBag(player, Diem);
                                PlayerDAO.addDiemSuKien(player, 10);


        InventoryService.gI().sendItemBags(player);
    }

    public static void Qua_4(Player player) {
        Item ThoiVang = ItemService.gI().createNewItem((short) 457, 128);
        Item PhieuGiamGia = ItemService.gI().createNewItem((short) 459, 10);
        Item DaBaoVe = ItemService.gI().createNewItem((short) 987, 20);
        Item CaiTrang = ItemService.gI().createNewItem((short) 1731, 1); // 1732 Black Goku | 1731 Black Goku Rose
        Item PhuKien = ItemService.gI().createNewItem((short) 1722, 1); // Cá Koi Zombie 1721  | Cánh 1722
        Item Pet = ItemService.gI().createNewItem((short) 1629, 1);  // Chó 1654 | Capybara 1629
        Item TheTieuDoiTruongVang = ItemService.gI().createNewItem((short) 956, 20); // Đội Trưởng Vàng 956 | Namek 1204
        Item CapsuleThanLinh = ItemService.gI().createNewItem((short) 1775, 2);
        Item TheRongThanNamek = ItemService.gI().createNewItem((short) 1204, 20);
        Item Diem = ItemService.gI().createNewItem((short)1999,15);

        // Option ThoiVang
        ThoiVang.itemOptions.add(new ItemOption(100, 1));

        // Option PhieuGiamGia
        PhieuGiamGia.itemOptions.add(new ItemOption(73, 1));

        // Option DaBaoVe
        DaBaoVe.itemOptions.add(new ItemOption(73, 1));

        // Option CaiTrang
        CaiTrang.itemOptions.add(new ItemOption(50, 40));
        CaiTrang.itemOptions.add(new ItemOption(103, 40));
        CaiTrang.itemOptions.add(new ItemOption(77, 40));
        CaiTrang.itemOptions.add(new ItemOption(5, 19));
        CaiTrang.itemOptions.add(new ItemOption(14, 15));
         CaiTrang.itemOptions.add(new ItemOption(101, 100));

        // Option PhuKien
        PhuKien.itemOptions.add(new ItemOption(50, 10));
        PhuKien.itemOptions.add(new ItemOption(77, 10));
        PhuKien.itemOptions.add(new ItemOption(103, 10));
        PhuKien.itemOptions.add(new ItemOption(101, 10));

        // Option Pet
        Pet.itemOptions.add(new ItemOption(50, 10));
        Pet.itemOptions.add(new ItemOption(77, 10));
        Pet.itemOptions.add(new ItemOption(103, 10));
        Pet.itemOptions.add(new ItemOption(101, 10));

        // Option TheDoiTruongVang
        TheTieuDoiTruongVang.itemOptions.add(new ItemOption(73, 0));

        // Option TheRongThanNamek
        TheRongThanNamek.itemOptions.add(new ItemOption(73, 0));
        
        // Option Diem
        Diem.itemOptions.add(new ItemOption(30,0));

        // Cập Nhật & Gửi Item Đến Balo Player
        InventoryService.gI().addItemBag(player, ThoiVang);
        InventoryService.gI().addItemBag(player, PhieuGiamGia);
        InventoryService.gI().addItemBag(player, DaBaoVe);
        InventoryService.gI().addItemBag(player, CaiTrang);
        InventoryService.gI().addItemBag(player, PhuKien);
        InventoryService.gI().addItemBag(player, Pet);
        InventoryService.gI().addItemBag(player, TheTieuDoiTruongVang);
        InventoryService.gI().addItemBag(player, CapsuleThanLinh);
        InventoryService.gI().addItemBag(player, TheRongThanNamek);
        InventoryService.gI().addItemBag(player, Diem);
                        PlayerDAO.addDiemSuKien(player, 15);


        InventoryService.gI().sendItemBags(player);
    }
}
