package boss.eventSummer;

import boss.BossID;
import boss.Boss;
import boss.BossesData;
import item.Item;
import java.util.List;
import map.ItemMap;
import player.Player;
import services.EffectSkillService;
import services.ItemService;
import services.Service;
import services.TaskService;
import utils.Util;

public class NamekSummer extends Boss {

    private long st;

    public NamekSummer() throws Exception {
        super(BossID.NAMEKSUMMER, BossesData.NAMEKSUMMER);
    }

    @Override
    public void reward(Player plKill) {
         int x = this.location.x;
        int y = this.zone.map.yPhysicInTop(x, this.location.y - 24);
        
        if (Util.isTrue(100, 100)) {
            
        ItemMap it = ItemService.gI().randDoTLBoss(this.zone, 1, x, y, plKill.id);
       
            Service.gI().dropItemMap(zone, it);
        
    
    int[] itemIds = {1010, 1011, 1012};
    int itemId = itemIds[Util.nextInt(0, itemIds.length-1)];

    ItemMap customItemMap = new ItemMap(this.zone, itemId, 1, x, y, plKill.id);

    // Tạo danh sách chỉ số
    customItemMap.options.add(new Item.ItemOption(77, Util.nextInt(20, 27)));
    customItemMap.options.add(new Item.ItemOption(103, Util.nextInt(20, 27)));
    customItemMap.options.add(new Item.ItemOption(50, Util.nextInt(20, 27)));
    customItemMap.options.add(new Item.ItemOption(101, Util.nextInt(20, 27)));
    customItemMap.options.add(new Item.ItemOption(95, Util.nextInt(10, 15)));
    customItemMap.options.add(new Item.ItemOption(96, Util.nextInt(10, 15)));

    // Chỉ số 231: hạn sử dụng (đặt -1 nếu muốn là vĩnh viễn)
    customItemMap.options.add(new Item.ItemOption(231, -1)); // -1 = vĩnh viễn, đổi nếu muốn

    // Thả vật phẩm ra map
    Service.gI().dropItemMap(zone, customItemMap);
}

     
    }

    @Override
    public synchronized int injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (Util.isTrue(10, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            return (int) damage;
        } else {
            return 0;
        }
    }
    @Override
    public void joinMap() {
        super.joinMap();
        st = System.currentTimeMillis();
    }

    @Override
    public void autoLeaveMap() {
        if (Util.canDoWithTime(st, 900000)) {
            this.leaveMapNew();
        }
        if (this.zone != null && this.zone.getNumOfPlayers() > 0) {
            st = System.currentTimeMillis();
        }
    }

}
