package npc.list;

/*
 * @Author: NROTIN
 * @Description: NRO T&T - May Chu Rieng
 */
import consts.ConstNpc;
import database.PlayerDAO;
import database.PlayerDAO;
import item.Item;
import item.Item.ItemOption;
import npc.Npc;
import player.Player;
import server.Manager;
import server.ServerManager;
import services.ItemService;
import services.PetService;
import services.Service;
import services.TaskService;
import player.Service.InventoryService;
import system.QuaToriBot;
import utils.Util;

public class ToriBot extends Npc {

    public ToriBot(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        super(mapId, status, cx, cy, tempId, avartar);
    }

    @Override
    public void openBaseMenu(Player player) {
        if (canOpenNpc(player)) {
            if (this.mapId == 0 || this.mapId == 7 || this.mapId == 14) {
                this.createOtherMenu(player, 100, "|7| Trong thời gian mùa 10 diễn ra\n"
                        + "( từ " + Manager.TIME_VIP_START + " đến hết " + Manager.TIME_VIP_END + ")\n"
                        + "|0| Tạo nhân vật mới sẽ được X2 Kinh nghiệm toàn mùa\n"
                        + "|0| Nếu nâng vip sẽ được nhận\nnhiều ưu đãi hơn nữa.\n"
                        + "|0| Lưu Ý: nâng cấp VIP chỉ được 4 lần mỗi mùa\n",
                        "Vip 1", "Vip 2", "Vip 3", "Vip 4", "Status", "Đóng");
            }
        }
    }

    @Override
    public void confirmMenu(Player player, int select) {
        if (canOpenNpc(player)) {
            switch (player.idMark.getIndexMenu()) {
                case 100 -> { // Đây là menu chọn cấp VIP
                    switch (select) {
                        case 4 -> { // Tình Trạng VIP
                            this.createOtherMenu(player, 3422,
                                    "|0| VIP STATUS"
                                    + (player.vip == 1 ? "\n|7|Status VIP : VIP 1" : player.vip == 2 ? "\n|7|Trạng Thái VIP : VIP 2" : player.vip == 3 ? "\n|7|Trạng Thái VIP : VIP 3" : player.vip == 4 ? "\n|7|Trạng Thái VIP : VIP 4" : "")
                                    + "\n|0|Cảm Ơn Đã Ủng Hộ Ngọc Rồng Chill",
                                    //  + (player.timevip > 0 ? "\nHạn còn : " + Util.msToThang(player.timevip) : ""),
                                    "Đóng");
                        }
                        case 0 -> { // Chọn VIP1
                            this.createOtherMenu(player, 223,
                                    "|0|Nâng Cấp VIP 1: 500.000 Điểm Mùa\n"
                                    + "- Tặng 1 Đệ Tử\n"
                                    + "- 16 Thỏi Vàng\n"
                                   // + "- 10 Phiếu Giảm Giá 80%\n"
                                    + "- 5 Đá Bảo Vệ\n"
                                    + "- Cải Trang Black Goku 30 Ngày\n"
                                    + "- Cá Zombie 30 Ngày\n"
                                    + "- Pet Chó 3 Đầu Địa Ngục 30 Ngày\n"
                                    + "- 5 điểm sự kiện",
                                    
                                    "50.000 VND", "Đóng");
                        }
                        case 1 -> { // Chọn VIP2
                            this.createOtherMenu(player, 224,
                                    "|0|Nâng Cấp VIP 2: 1.000.000 Điểm Mùa\n"
                                    + "- Tặng 1 Đệ Tử\n"
                                    + "- 32 Thỏi Vàng\n"
                                    //+ "- 10 Phiếu Giảm Giá 80%\n"
                                    + "- 10 Thẻ Rồng Thần Namek\n"
                                    + "- 10 Đá Bảo Vệ\n"
                                    + "- Cải Trang Black Goku Vĩnh Viễn\n"
                                    + "- Cá Zombie Vĩnh Viễn\n"
                                    + "- Pet Chó 3 Đầu Địa Ngục Vĩnh Viễn\n"
                                    + "- 7 điểm sự kiện",
                                    "100.000 VND", "Đóng");
                        }
                        case 2 -> { // Chọn VIP3
                            this.createOtherMenu(player, 225,
                                    "|0|Nâng Cấp VIP 3: 3.000.000 Điểm Mùa\n"
                                    + "- Tặng 1 Đệ Tử\n"
                                    + "- 64 Thỏi Vàng\n"
                                    //+ "- 10 Phiếu Giảm Giá 80%\n"
                                    + "- 20 Đá Bảo Vệ\n"
                                    + "- 10 Thẻ Tiểu Đội Trưởng Vàng\n"
                                    + "- Cải Trang Black Goku Rose 30 Ngày\n"
                                    + "- 1 Capsule Thần Linh\n"
                                    + "- Cánh Thiên Thần - Ác Quỷ 30 Ngày\n"
                                    + "- Pet Capybara 30 Ngày\n"
                                    + "- 10 điểm sự kiện",
                                    "150.000 VND", "Đóng");
                        }
                        case 3 -> { // Chọn SVIP
                            this.createOtherMenu(player, 226,
                                    "|0|Nâng Cấp VIP 4: 5.000.000 Điểm Mùa\n"
                                    + "- Tặng 1 Đệ Tử\n"
                                    + "- 128 Thỏi Vàng\n"
                                    //+ "- 10 Phiếu Giảm Giá 80%\n"
                                    + "- 50 Đá Bảo Vệ\n"
                                    + "- 20 Thẻ Tiểu Đội Trưởng Vàng & Namek\n"
                                    + "- Cải Trang Black Goku Rose 30 Ngày\n"
                                    + "- 2 Capsule Đồ Thần Linh\n"
                                    + "- Cánh Thiên Thần - Ác Quỷ Vĩnh Viễn\n"
                                    + "- Pet Capybara Vĩnh Viễn\n"
                                    + "- 15 điểm sự kiện",
                                    "200.000VND", "Đóng");
                        }
                    }
                }
                case 223 -> { // Kích Hoạt VIP1
                    if (select == 0) {
                        if (!PlayerDAO.subvip(player, 1)) {
                            this.npcChat(player, "bạn đã hết lượt mua của tháng này");
                            return;
                        }
                        if (player.getSession().cash >= 50_000) {
                            if (InventoryService.gI().getCountEmptyBag(player) < 7) {
                                this.npcChat(player, "cần 7 ô trống hành trang");
                                return;
                            }
                            // Reset hoặc kích hoạt VIP mới
                            player.vip = 1;
                            player.timevip = System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30);
                            QuaToriBot.Qua_1(player);
                            PetService.gI().createNormalPet(player, Util.nextInt(0, 2));

                            // Trừ điểm và subvip
                            PlayerDAO.subcash(player, 50_000);
                            this.npcChat(player, "Kích hoạt thành công: VIP 1");
                        } else {
                            Service.gI().sendThongBaoOK(player, "điểm tích lũy chưa đủ.\nTruy Cập: " + ServerManager.DOMAIN + "\n để nạp thêm");
                        }
                    }
                    break;
                }
                case 224 -> { // Kích Hoạt VIP2
                    if (select == 0) {
                        if (!PlayerDAO.subvip(player, 1)) {
                            this.npcChat(player, "bạn đã hết lượt mua của tháng này");
                            return;
                        }
                        if (player.getSession().cash >= 100_000) {
                            if (InventoryService.gI().getCountEmptyBag(player) < 8) {
                                this.npcChat(player, "cần 8 ô trống hành trang");
                                return;
                            }
                            // Reset hoặc kích hoạt VIP mới
                            player.vip = 2;
                            player.timevip = System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30);
                            QuaToriBot.Qua_2(player);
                            PetService.gI().createNormalPet(player, Util.nextInt(0, 2));

                            // Trừ điểm và subvip
                            PlayerDAO.subcash(player, 100_000);
                            this.npcChat(player, "Kích hoạt thành công: VIP 2");
                        } else {
                            Service.gI().sendThongBaoOK(player, "điểm tích lũy chưa đủ.\nTruy Cập: " + ServerManager.DOMAIN + "\n để nạp thêm");
                        }
                    }
                    break;
                }
                case 225 -> { // Kích Hoạt VIP3
                    if (select == 0) {
                        if (!PlayerDAO.subvip(player, 1)) {
                            this.npcChat(player, "bạn đã hết lượt mua của tháng này");
                            return;
                        }
                        if (player.getSession().cash >= 150_000) {
                            if (InventoryService.gI().getCountEmptyBag(player) < 8) {
                                this.npcChat(player, "cần 8 ô trống hành trang");
                                return;
                            }
                            // Reset hoặc kích hoạt VIP mới
                            player.vip = 3;
                            player.timevip = System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30);
                            QuaToriBot.Qua_3(player);
                            PetService.gI().createNormalPet(player, Util.nextInt(0, 2));

                            // Trừ điểm và subvip
                            PlayerDAO.subcash(player, 150_000);
                            this.npcChat(player, "Kích hoạt thành công: VIP 3");
                        } else {
                            Service.gI().sendThongBaoOK(player, "điểm tích lũy chưa đủ.\nTruy Cập: " + ServerManager.DOMAIN + "\n để nạp thêm");
                        }
                    }
                    break;
                }
                case 226 -> { // Kích Hoạt VIP4
                    if (select == 0) {
                        if (!PlayerDAO.subvip(player, 1)) {
                            this.npcChat(player, "bạn đã hết lượt mua của tháng này");
                            return;
                        }
                        if (player.getSession().cash >= 200_000) {
                            if (InventoryService.gI().getCountEmptyBag(player) < 9) {
                                this.npcChat(player, "cần 9 ô trống hành trang");
                                return;
                            }
                            // Reset hoặc kích hoạt VIP mới
                            player.vip = 4;
                            player.timevip = System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30);
                            QuaToriBot.Qua_4(player);
                            PetService.gI().createNormalPet(player, Util.nextInt(0, 2));

                            // Trừ điểm và subvip
                            PlayerDAO.subcash(player, 200_000);
                            this.npcChat(player, "Kích hoạt thành công: VIP 4");
                        } else {
                            Service.gI().sendThongBaoOK(player, "điểm tích lũy chưa đủ.\nTruy Cập: " + ServerManager.DOMAIN + "\n để nạp thêm");
                        }
                    }
                    break;
                }
            }
        }
    }
}
