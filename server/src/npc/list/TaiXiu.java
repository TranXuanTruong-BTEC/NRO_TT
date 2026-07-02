///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package npc.list;
//
//import servive.func.TaiXiu;
//
//public class TaiXiu {
//    
//                        String time = ((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) + " giây";
//                        if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && player.goldTai == 0 && player.goldXiu == 0 && TaiXiu.gI().baotri == false) {
//                            switch (select) {
//                                case 0:
//                                    NpcService.gI().createMenuConMeo(player, ConstNpc.TAIXIU, 11039, "\n|7|---NHÀ CÁI TÀI XỈU---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
//                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
//                                            + "\n\nTổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
//                                    break;
//                                case 1:
//                                    if (TaskService.gI().getIdTask(player) >= ConstTask.TASK_24_0) {
//                                        Input.gI().TAI_taixiu(player);
//                                    } else {
//                                        Service.getInstance().sendThongBao(player, "Bạn chưa đủ điều kiện để chơi");
//                                    }
//                                    break;
//                                case 2:
//                                    if (TaskService.gI().getIdTask(player) >= ConstTask.TASK_24_0) {
//                                        Input.gI().XIU_taixiu(player);
//                                    } else {
//                                        Service.getInstance().sendThongBao(player, "Bạn chưa đủ điều kiện để chơi");
//                                    }
//                                    break;
//                            }
//                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && player.goldTai > 0 && TaiXiu.gI().baotri == false) {
//                            switch (select) {
//                                case 0:
//                                    NpcService.gI().createMenuConMeo(player, ConstNpc.TAIXIU, 11039, "\n|7|---NHÀ CÁI TÀI XỈU---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
//                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
//                                            + "\n\nTổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc\n\n|5|Thời gian còn lại: " + time + "\n\n|7|Bạn đã cược Tài : " + Util.format(player.goldTai) + " Hồng ngọc", "Cập nhập", "Đóng");
//                                    break;
//                            }
//                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && player.goldXiu > 0 && TaiXiu.gI().baotri == false) {
//                            switch (select) {
//                                case 0:
//                                    NpcService.gI().createMenuConMeo(player, ConstNpc.TAIXIU, 11039, "\n|7|---NHÀ CÁI TÀI XỈU---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
//                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
//                                            + "\n\nTổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc\n\n|5|Thời gian còn lại: " + time + "\n\n|7|Bạn đã cược Xỉu : " + Util.format(player.goldXiu) + " Hồng ngọc", "Cập nhập", "Đóng");
//                                    break;
//                            }
//                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && player.goldTai > 0 && TaiXiu.gI().baotri == true) {
//                            switch (select) {
//                                case 0:
//                                    NpcService.gI().createMenuConMeo(player, ConstNpc.TAIXIU, 11039, "\n|7|---NHÀ CÁI TÀI XỈU---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
//                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
//                                            + "\n\nTổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc\n\n|5|Thời gian còn lại: " + time + "\n\n|7|Bạn đã cược Tài : " + Util.format(player.goldTai) + " Hồng ngọc" + "\n\n|7|Hệ thống sắp bảo trì", "Cập nhập", "Đóng");
//                                    break;
//                            }
//                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && player.goldXiu > 0 && TaiXiu.gI().baotri == true) {
//                            switch (select) {
//                                case 0:
//                                    NpcService.gI().createMenuConMeo(player, ConstNpc.TAIXIU, 11039, "\n|7|---NHÀ CÁI TÀI XỈU---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
//                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
//                                            + "\n\nTổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc\n\n|5|Thời gian còn lại: " + time + "\n\n|7|Bạn đã cược Xỉu : " + Util.format(player.goldXiu) + " Hồng ngọc" + "\n\n|7|Hệ thống sắp bảo trì", "Cập nhập", "Đóng");
//                                    break;
//                            }
//                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && player.goldXiu == 0 && player.goldTai == 0 && TaiXiu.gI().baotri == true) {
//                            switch (select) {
//                                case 0:
//                                    NpcService.gI().createMenuConMeo(player, ConstNpc.TAIXIU, 11039, "\n|7|---NHÀ CÁI TÀI XỈU---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
//                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
//                                            + "\n\nTổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc\n\n|5|Thời gian còn lại: " + time + "\n\n|7|Hệ thống sắp bảo trì", "Cập nhập", "Đóng");
//                                    break;
//                            }
//                        }
//                        break;
//}
