package npc.list;

import consts.ConstNpc;
import database.PlayerDAO;
import item.Item;
import map.Service.ChangeMapService;
import map.Service.NpcService;
import npc.Npc;
import player.Player;
import player.PlayerEvent;
import services.Service;
import services.TaskService;
import services.func.Input;
import player.Service.InventoryService;
import services.ItemService;
import shop.ShopService;

public class LyTieuNuong extends Npc {

    public LyTieuNuong(int mapid, int status, int cx, int cy, int tempid, int avartar) {
        super(mapid, status, cx, cy, tempid, avartar);
    }

    @Override
    public void openBaseMenu(Player player) {
        if (canOpenNpc(player) && !TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
            createOtherMenu(player, ConstNpc.BASE_MENU,
                    "EVENT HÈ 2025-NRO T&T",
                    "HƯỚNG DẪN THÊM",
                    "ĐỔI ĐIỂM", "SHOP EVENT","Đổi hộp quà","Đổi hộp quà VIP","Đến bãi biển Kame"
            ,"Đổi thỏi vàng");
        }
    }

    @Override
    public void confirmMenu(Player player, int select) {
        if (canOpenNpc(player) && player.idMark.isBaseMenu()) {
            switch (select) {
                case 0 -> {
                    NpcService.gI().createTutorial(player, tempId, this.avartar, ConstNpc.HUONG_DAN_EVENT);
                    break;
                }
                case 1->{
                    Item traiDua=null;
                    Item voOc=null;
                    Item voSo=null;
                    Item conCua=null;
                    Item saoBien=null;
                    try{
                    traiDua=InventoryService.gI().findItemBagByTemp(player, (short)694);
                    voOc=InventoryService.gI().findItemBagByTemp(player, (short)695);
                    voSo=InventoryService.gI().findItemBagByTemp(player,(short) 696);
                    conCua=InventoryService.gI().findItemBagByTemp(player,(short) 697);
                    saoBien=InventoryService.gI().findItemBagByTemp(player,(short) 698);
                    
                    }catch(Exception e){}
                    if(player.inventory.gem<1000){
                    Service.gI().sendThongBao(player, "Kiếm được 1000 ngọc xanh rồi nói chuyện"); 
                    return;
                    }
                    else{
//                        Input.gI().createFormDoiDiemSuKiuen(player);
                        if(traiDua==null || traiDua.quantity < 10
                         ||voOc==null || voOc.quantity < 10
                         ||voSo==null || voSo.quantity < 10
                         ||conCua==null || conCua.quantity < 10
                         ||saoBien==null || saoBien.quantity < 10     
                          ){
                            Service.gI().sendThongBao(player, "Không đủ nguyên liệu chồng ơi");
                            return;
                        }
                        if(InventoryService.gI().getCountEmptyBag(player)==0){
                            Service.gI().sendThongBao(player, "Không đủ chỗ trống trong hành trang chồng ơi");
                            return;
                        }
                        else{
                            
                            int slNguyenLieu = Math.min(traiDua.quantity / 10,
                          Math.min(voOc.quantity / 10,
                          Math.min(voSo.quantity / 10,
                          Math.min(conCua.quantity / 10, saoBien.quantity / 10))));

    int slDiemTuNgoc = player.inventory.gem / 1000;

    int soLuongDoi = Math.min(slNguyenLieu, slDiemTuNgoc);
                            Item Diem=ItemService.gI().createNewItem((short)1999, soLuongDoi);
                            
                            Diem.itemOptions.add(new Item.ItemOption(30, 0));
                            
                            InventoryService.gI().addItemBag(player, Diem);
                            
                           
                            
                            Service.gI().sendMoney(player);
                            
                            Service.gI().sendThongBao(player, "Bạn đã nhận được " +soLuongDoi+" Điểm");
//                         player.diemSuKien+=soLuongDoi;
                            PlayerDAO.addDiemSuKien(player, soLuongDoi);

                            player.inventory.gem-=1000*soLuongDoi;
                            InventoryService.gI().subQuantityItemsBag(player, traiDua, 10*soLuongDoi);
                            InventoryService.gI().subQuantityItemsBag(player, voOc, 10*soLuongDoi);
                            InventoryService.gI().subQuantityItemsBag(player, voSo, 10*soLuongDoi);
                            InventoryService.gI().subQuantityItemsBag(player, conCua, 10*soLuongDoi);
                            InventoryService.gI().subQuantityItemsBag(player, saoBien, 10*soLuongDoi);


                            return;

                        }
                    }

                   
                }
                case 2->{
                            ShopService.gI().opendShop(player, "SHOP_EVENT", true);                    
                    break;
                }
                case 3->{
                    Item Diem=null;
                    Diem=InventoryService.gI().findItemBagByTemp(player, (short)1999);
                    if(Diem==null || Diem.quantity <0)
                         {
                            Service.gI().sendThongBao(player, "Không đủ nguyên liệu chồng ơi");
                            return;
                        }
                        if(InventoryService.gI().getCountEmptyBag(player)==0){
                            Service.gI().sendThongBao(player, "Không đủ chỗ trống trong hành trang chồng ơi");
                            return;
                        }
                        else{
                          //  Item Diem=ItemService.gI().createNewItem((short)1999, 1);
                            Item hopQuaThuong=ItemService.gI().createNewItem((short)1537, 1);
                            hopQuaThuong.itemOptions.add(new Item.ItemOption(30, 0));
                            InventoryService.gI().subQuantityItemsBag(player, Diem, 2);
                            
                            InventoryService.gI().addItemBag(player, hopQuaThuong);
                            
                            Service.gI().sendThongBao(player, "Bạn đã nhận được 1 Hộp Quà Thường");
                    break;
                }
                
            }
                case 4->{
                    Item Diem=null;
                    Diem=InventoryService.gI().findItemBagByTemp(player, (short)1999);
                    if(Diem==null || Diem.quantity <10)
                         {
                            Service.gI().sendThongBao(player, "Không đủ Điểm chồng ơi");
                            return;
                        }
                        if(InventoryService.gI().getCountEmptyBag(player)==0){
                            Service.gI().sendThongBao(player, "Không đủ chỗ trống trong hành trang chồng ơi");
                            return;
                        }
                        else{
                          //  Item Diem=ItemService.gI().createNewItem((short)1999, 1);
                            Item hopQuaVIP=ItemService.gI().createNewItem((short)1538, 1);
                            hopQuaVIP.itemOptions.add(new Item.ItemOption(30, 0));
                            InventoryService.gI().subQuantityItemsBag(player, Diem, 10);
                            
                            InventoryService.gI().addItemBag(player, hopQuaVIP);
                            
                            Service.gI().sendThongBao(player, "Bạn đã nhận được 1 Hộp Quà Vip");
                    break;
                }
                }
                case 5 ->{
                    ChangeMapService.gI().changeMapNonSpaceship(player, 165, 792,0);
                }
                 case 6 -> {
                    Input.gI().createFormTradeGold(player);
                }
                
                    
                }
            }
            
        }
    }
