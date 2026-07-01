package combine;

import consts.ConstNpc;
import item.Item;
import item.Item.ItemOption;
import player.Player;
import player.Service.InventoryService;
import services.ItemService;
import services.Service;
import utils.Util;

public class NangCapBongTaiCap3 {

    // Constants
    private static final int GOLD_BONG_TAI = 200_000_000;
    private static final int GEM_BONG_TAI = 1_000;
    private static final int RATIO_BONG_TAI = 50;
    private static final int ITEM_ID_BONG_TAI_C2 = 921;
    private static final int ITEM_ID_BONG_TAI_C3 = 1998;
    private static final int ITEM_ID_MANH_VO_BT_C3 = 1997;
    private static final int ITEM_ID_HON_XANH_LAM = 1996;
    private static final int ITEM_OPTION_ID_CAP = 72;
    private static final int ITEM_OPTION_VALUE_CAP_3 = 3;
    private static final int ITEM_PARAM_INDEX = 31;
    private static final int REQUIRED_MANH_VO_FULL = 9999;
    private static final int REQUIRED_MANH_VO_FAIL = 99;
    private static final int REQUIRED_PARAM_HON = 10;

    public static void showInfoCombine(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            Item bongTai = null;
            Item manhVo = null;
            Item hon = null;

            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.id == ITEM_ID_BONG_TAI_C2) {
                    bongTai = item;
                } else if (item.template.id == ITEM_ID_MANH_VO_BT_C3) {
                    manhVo = item;
                } else if (item.template.id == ITEM_ID_HON_XANH_LAM) {
                    hon = item;
                }
            }

            if (bongTai != null && manhVo != null && hon != null) {
                player.combineNew.goldCombine = GOLD_BONG_TAI;
                player.combineNew.gemCombine = GEM_BONG_TAI;
                player.combineNew.ratioCombine = RATIO_BONG_TAI;

                int currentMVBT3 = InventoryService.gI().getParam(player, ITEM_PARAM_INDEX, ITEM_ID_MANH_VO_BT_C3);
                int currentHXL = InventoryService.gI().getParam(player, ITEM_PARAM_INDEX, ITEM_ID_HON_XANH_LAM);

                StringBuilder npcSay = new StringBuilder("|2|Bông tai Porata [+2]\n\n");
                npcSay.append("|2|Tỉ lệ thành công: ").append(RATIO_BONG_TAI).append("%\n");

                if (currentMVBT3 < REQUIRED_MANH_VO_FULL || currentHXL < REQUIRED_PARAM_HON) {
                    npcSay.append("|7|Cần ").append(REQUIRED_MANH_VO_FULL).append(" ").append(manhVo.template.name).append(" (có: ").append(currentMVBT3).append(")\n");
                    npcSay.append("|7|Cần ").append(REQUIRED_PARAM_HON).append(" hồn xanh lục (có: ").append(currentHXL).append(")\n");
                    npcSay.append("|2|Cần: ").append(Util.numberToMoney(GOLD_BONG_TAI)).append(" vàng\n");
                    npcSay.append("|2|Cần: ").append(GEM_BONG_TAI).append(" ngọc\n");
                    npcSay.append("|7|Thất bại -").append(REQUIRED_MANH_VO_FAIL).append(" ").append(manhVo.template.name);
                    CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay.toString(), "Đóng");
                } else {
                    npcSay.append("|2|Cần ").append(REQUIRED_MANH_VO_FULL).append(" ").append(manhVo.template.name).append("\n");
                    npcSay.append("|2|Cần ").append(REQUIRED_PARAM_HON).append(" hồn xanh lục\n");
                    npcSay.append("|2|Cần: ").append(GEM_BONG_TAI).append(" ngọc\n");
                    npcSay.append("|2|Cần: ").append(Util.numberToMoney(GOLD_BONG_TAI)).append(" vàng\n");
                    npcSay.append("|7|Thất bại -").append(REQUIRED_MANH_VO_FAIL).append(" ").append(manhVo.template.name);
                    CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay.toString(), "Nâng cấp\n" + Util.numberToMoney(GOLD_BONG_TAI) + " vàng\n" + GEM_BONG_TAI + " ngọc", "Từ chối");
                }
            } else {
                CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                        "Cần 1 Bông tai Porata cấp 2, 9999 mảnh vỡ bông tai cấp 3 và 10 hồn xanh lục", "Đóng");
            }
        } else {
            CombineService.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Cần 3 vật phẩm: Bông tai C2, Mảnh vỡ BTC3 và Hồn Xanh Lục", "Đóng");
        }
    }

    public static void nangCapBongTai(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            int gold = GOLD_BONG_TAI;
            int gem = GEM_BONG_TAI;

            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }

            Item bongTai = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.id == ITEM_ID_BONG_TAI_C2) {
                    bongTai = item;
                    break;
                }
            }

            if (bongTai != null) {
                int currentMVBT3 = InventoryService.gI().getParam(player, ITEM_PARAM_INDEX, ITEM_ID_MANH_VO_BT_C3);
                int currentHXL = InventoryService.gI().getParam(player, ITEM_PARAM_INDEX, ITEM_ID_HON_XANH_LAM);

                if (currentMVBT3 < REQUIRED_MANH_VO_FULL) {
                    Service.gI().sendThongBao(player, "Thiếu mảnh vỡ bông tai cấp 3!");
                    return;
                }

                if (currentHXL < REQUIRED_PARAM_HON) {
                    Service.gI().sendThongBao(player, "Thiếu Hồn xanh lục!");
                    return;
                }

                player.inventory.gold -= gold;
                player.inventory.gem -= gem;

                if (Util.isTrue(RATIO_BONG_TAI, 100)) {
                    bongTai.template = ItemService.gI().getTemplate(ITEM_ID_BONG_TAI_C3);
                    bongTai.itemOptions.clear();
                    bongTai.itemOptions.add(new ItemOption(ITEM_OPTION_ID_CAP, ITEM_OPTION_VALUE_CAP_3));
                    InventoryService.gI().subParamItemsBag(player, ITEM_ID_MANH_VO_BT_C3, ITEM_PARAM_INDEX, REQUIRED_MANH_VO_FULL);
                    InventoryService.gI().subParamItemsBag(player, ITEM_ID_HON_XANH_LAM, ITEM_PARAM_INDEX, REQUIRED_PARAM_HON);
                    CombineService.gI().sendEffectSuccessCombine(player);
                } else {
                    InventoryService.gI().subParamItemsBag(player, ITEM_ID_MANH_VO_BT_C3, ITEM_PARAM_INDEX, REQUIRED_MANH_VO_FAIL);
                    CombineService.gI().sendEffectFailCombine(player);
                }

                InventoryService.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                CombineService.gI().reOpenItemCombine(player);
            }
        }
    }
}
