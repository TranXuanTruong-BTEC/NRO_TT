package combine;

import consts.ConstNpc;
import item.Item;
import player.Player;
import player.Service.InventoryService;
import services.Service;
import utils.Util;

public class NangChiSoBongTaiCap3 {
    private static final int GOLD_BONG_TAI = 200_000_000;
    private static final int GEM_BONG_TAI = 1_000;
    private static final int GEM_NANG_BT = 1_000;
    private static final int RATIO_BONG_TAI = 50;
    private static final int RATIO_NANG_CAP = 45;
    private static final int BONG_TAI_ID = 1998;
    private static final int HON_BONG_TAI_ID = 934;
    private static final int DA_XANH_LAM_ID = 935;
    private static final int ITEM_PARAM_INDEX = 31;
    private static final byte[] UPGRADE_OPTIONS = {77, 80, 81, 103, 50, 94, 5};
    private static final byte OPTION_ID = 0;
    private static final byte PARAM_MIN = 10;
    private static final byte PARAM_MAX = 30;
    private static final int REQUIRED_MANH_HON_FULL = 9999;
    private static final int REQUIRED_MANH_HON_FAIL = 99;
    private static final int REQUIRED_DA_XANH_LAM_FULL = 100;
    private static final int REQUIRED_DA_XANH_LAM_FAIL = 30;
    public static void showInfoCombine(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            Item bongTai = null;
            Item honBongTai = null;
            Item daXanhLam = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    switch (item.template.id) {
                        case BONG_TAI_ID -> bongTai = item;
                        case HON_BONG_TAI_ID -> honBongTai = item;
                        case DA_XANH_LAM_ID -> daXanhLam = item;
                    }
                }
            }
            if (bongTai != null && honBongTai != null && daXanhLam != null) {

                player.combineNew.goldCombine = GOLD_BONG_TAI;
                player.combineNew.gemCombine = GEM_NANG_BT;
                player.combineNew.ratioCombine = RATIO_NANG_CAP;
                
                
               
                
                String npcSay = "|2|Bông tai Porata [+3]" + "\n\n";
                npcSay += "|2|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                int currentMHBT = InventoryService.gI().getParam(player, ITEM_PARAM_INDEX, HON_BONG_TAI_ID);
                int currentDXL = InventoryService.gI().getParam(player, ITEM_PARAM_INDEX, DA_XANH_LAM_ID);
                if (currentDXL < 1) {
                    npcSay += "|2|Cần 9999 " + honBongTai.template.name + "\n";
                    npcSay += "|7|Cần 100 " + daXanhLam.template.name + "\n";
                    npcSay += "|2|Cần: " + player.combineNew.gemCombine + " ngọc\n";
                    npcSay += "|1|+1 Chỉ số ngẫu nhiên";
                    npcSay += "|2|Còn thiếu\n" + (1 - currentDXL) + " " + daXanhLam.template.name;
                    CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                } else if (currentMHBT < 99) {
                    npcSay += "|7|Cần 9999 " + honBongTai.template.name + "\n";
                    npcSay += "|2|Cần 100 " + daXanhLam.template.name + "\n";
                    npcSay += "|2|Cần: " + player.combineNew.gemCombine + " ngọc\n";
                    npcSay += "|1|+1 Chỉ số ngẫu nhiên";
                    npcSay += "|2|Còn thiếu\n" + (99 - currentMHBT) + " " + honBongTai.template.name;
                    CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                } else if (player.inventory.gem >= player.combineNew.gemCombine) {
                    npcSay += "|2|Cần 9999 " + honBongTai.template.name + "\n";
                    npcSay += "|2|Cần 100 " + daXanhLam.template.name + "\n";
                    npcSay += "|2|Cần: " + player.combineNew.gemCombine + " ngọc\n";
                    npcSay += "|1|+1 Chỉ số ngẫu nhiên";
                    CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                            "Nâng cấp\n" + player.combineNew.gemCombine + " ngọc", "Từ chối");
                } else if (player.inventory.gem < player.combineNew.gemCombine) {
                    npcSay += "|2|Cần 9999 " + honBongTai.template.name + "\n";
                    npcSay += "|2|Cần 100 " + daXanhLam.template.name + "\n";
                    npcSay += "|7|Cần: " + player.combineNew.gemCombine + " ngọc\n";
                    npcSay += "|1|+1 Chỉ số ngẫu nhiên";
                    npcSay += "|2|Còn thiếu\n" + (player.combineNew.gemCombine - player.inventory.gem) + " ngọc xanh";
                    CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                }
            } else {
                CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                        "Cần 1 Bông tai Porata cấp 3, X9999 Mảnh hồn bông tai và 100 Đá xanh lam", "Đóng");
            }
        } else {
            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Cần 1 Bông tai Porata cấp 3, X9999 Mảnh hồn bông tai và 100 Đá xanh lam", "Đóng");
        }
    }

    public static void nangChiSoBongTai(Player player) {
        try {
            if (player.inventory.gem < player.combineNew.gemCombine) {
                Service.gI().sendThongBao(player, "Bạn không đủ ngọc, còn thiếu " + Util.powerToString(player.combineNew.gemCombine - player.inventory.gem) + " ngọc nữa!");
                return;
            }
            player.inventory.gem -= player.combineNew.gemCombine;
            Service.gI().sendMoney(player);
            Item BongTai2 = null,
                    honBongTai = null,
                    daXanhLam = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.id == BONG_TAI_ID) {
                    BongTai2 = item;
                }
                if (item.template.id == HON_BONG_TAI_ID) {
                    honBongTai = item;
                }
                if (item.template.id == DA_XANH_LAM_ID) {
                    daXanhLam = item;
                }
            }
            
        
            if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                byte optionid = UPGRADE_OPTIONS[Util.nextInt(0, 6)];
                byte param = (byte) Util.nextInt(PARAM_MIN, PARAM_MAX);
                BongTai2.itemOptions.clear();
                BongTai2.itemOptions.add(new Item.ItemOption(optionid, param));
                BongTai2.itemOptions.add(new Item.ItemOption(38, 0));
                BongTai2.itemOptions.add(new Item.ItemOption(72, 2));
                CombineService.gI().sendEffectSuccessCombine(player);
                InventoryService.gI().subParamItemsBag(player, HON_BONG_TAI_ID, ITEM_PARAM_INDEX, REQUIRED_MANH_HON_FAIL);
            InventoryService.gI().subParamItemsBag(player, DA_XANH_LAM_ID, ITEM_PARAM_INDEX, REQUIRED_DA_XANH_LAM_FAIL);
            } else {
                CombineService.gI().sendEffectFailCombine(player);
                InventoryService.gI().subParamItemsBag(player, HON_BONG_TAI_ID, ITEM_PARAM_INDEX, REQUIRED_MANH_HON_FAIL);
            InventoryService.gI().subParamItemsBag(player, DA_XANH_LAM_ID, ITEM_PARAM_INDEX, REQUIRED_DA_XANH_LAM_FAIL);
                    }
            
            InventoryService.gI().sendItemBags(player);
            CombineService.gI().reOpenItemCombine(player);
            Service.gI().sendMoney(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}