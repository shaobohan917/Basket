package com.first.basket.bean;

import java.util.List;

/**
 * Created by hanshaobo on 15/09/2017.
 */

public class ClassifyBean {
    /**
     * status : 0
     * info : 成功
     * result : {"data":[{"leveloneid":"101","levelonedesc":"优质蔬菜","leveltwo":[{"leveltwoid":"101001","leveltwodesc":"叶菜类"},{"leveltwoid":"101002","leveltwodesc":"根茎类"},{"leveltwoid":"101003","leveltwodesc":"茄果类"},{"leveltwoid":"101004","leveltwodesc":"菌菇类"},{"leveltwoid":"101005","leveltwodesc":"其他蔬菜"},{"leveltwoid":"101006","leveltwodesc":"豆制品"},{"leveltwoid":"101007","leveltwodesc":"有机蔬菜"}]},{"leveloneid":"102","levelonedesc":"新鲜水果","leveltwo":[{"leveltwoid":"102001","leveltwodesc":"苹果类"},{"leveltwoid":"102002","leveltwodesc":"桃梨类"},{"leveltwoid":"102003","leveltwodesc":"柑橙类"},{"leveltwoid":"102004","leveltwodesc":"小果类"},{"leveltwoid":"102005","leveltwodesc":"瓜果类"},{"leveltwoid":"102006","leveltwodesc":"热带果"},{"leveltwoid":"102007","leveltwodesc":"其他水果"}]},{"leveloneid":"103","levelonedesc":"肉禽蛋品","leveltwo":[{"leveltwoid":"103001","leveltwodesc":"猪肉类"},{"leveltwoid":"103002","leveltwodesc":"牛羊类"},{"leveltwoid":"103003","leveltwodesc":"鸡鸭类"},{"leveltwoid":"103004","leveltwodesc":"禽蛋类"},{"leveltwoid":"103005","leveltwodesc":"肉制类"},{"leveltwoid":"103006","leveltwodesc":"其他肉类"}]},{"leveloneid":"104","levelonedesc":"海鲜水产","leveltwo":[{"leveltwoid":"104001","leveltwodesc":"淡水鱼"},{"leveltwoid":"104002","leveltwodesc":"海鱼类"},{"leveltwoid":"104003","leveltwodesc":"虾蟹类"},{"leveltwoid":"104004","leveltwodesc":"贝壳类"},{"leveltwoid":"104005","leveltwodesc":"其他海鲜"}]},{"leveloneid":"105","levelonedesc":"粮油米面","leveltwo":[{"leveltwoid":"105001","leveltwodesc":"大米粉面"},{"leveltwoid":"105002","leveltwodesc":"五谷杂粮"},{"leveltwoid":"105003","leveltwodesc":"食用油"},{"leveltwoid":"105004","leveltwodesc":"面条粉丝"},{"leveltwoid":"105005","leveltwodesc":"冲饮谷物"}]},{"leveloneid":"106","levelonedesc":"调味调料","leveltwo":[{"leveltwoid":"106001","leveltwodesc":"调味品"},{"leveltwoid":"106002","leveltwodesc":"调味酱料"},{"leveltwoid":"106003","leveltwodesc":"南北干货"},{"leveltwoid":"106004","leveltwodesc":"腌腊制品"}]},{"leveloneid":"107","levelonedesc":"乳品饮料","leveltwo":[{"leveltwoid":"107001","leveltwodesc":"牛奶类"},{"leveltwoid":"107002","leveltwodesc":"酸奶类"},{"leveltwoid":"107003","leveltwodesc":"其他乳品"},{"leveltwoid":"107004","leveltwodesc":"茶水饮料"},{"leveltwoid":"107005","leveltwodesc":"碳酸饮料"},{"leveltwoid":"107006","leveltwodesc":"其他饮料"},{"leveltwoid":"107007","leveltwodesc":"咖啡"},{"leveltwoid":"107008","leveltwodesc":"酒类"}]},{"leveloneid":"108","levelonedesc":"休闲食品","leveltwo":[{"leveltwoid":"108001","leveltwodesc":"各式坚果"},{"leveltwoid":"108002","leveltwodesc":"饼干糕点"},{"leveltwoid":"108003","leveltwodesc":"休闲小食"},{"leveltwoid":"108004","leveltwodesc":"糖果巧克力"}]},{"leveloneid":"109","levelonedesc":"方便速食","leveltwo":[{"leveltwoid":"109001","leveltwodesc":"方便面"},{"leveltwoid":"109002","leveltwodesc":"罐装食品"},{"leveltwoid":"109003","leveltwodesc":"冰冻食品"},{"leveltwoid":"109004","leveltwodesc":"火锅丸子"},{"leveltwoid":"109005","leveltwodesc":"净菜盘菜"}]},{"leveloneid":"110","levelonedesc":"礼品卡券","leveltwo":[{"leveltwoid":"110001","leveltwodesc":"礼盒"},{"leveltwoid":"110002","leveltwodesc":"卡券"}]},{"leveloneid":"111","levelonedesc":"日用杂品","leveltwo":[{"leveltwoid":"111001","leveltwodesc":"生活用品"},{"leveltwoid":"111002","leveltwodesc":"办公用品"},{"leveltwoid":"111003","leveltwodesc":"一次性用品"},{"leveltwoid":"111004","leveltwodesc":"厨房用品"}]}]}
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * leveloneid : 101
         * levelonedesc : 优质蔬菜
         * leveltwo : [{"leveltwoid":"101001","leveltwodesc":"叶菜类"},{"leveltwoid":"101002","leveltwodesc":"根茎类"},{"leveltwoid":"101003","leveltwodesc":"茄果类"},{"leveltwoid":"101004","leveltwodesc":"菌菇类"},{"leveltwoid":"101005","leveltwodesc":"其他蔬菜"},{"leveltwoid":"101006","leveltwodesc":"豆制品"},{"leveltwoid":"101007","leveltwodesc":"有机蔬菜"}]
         */

        private String leveloneid;
        private String levelonedesc;
        private List<LeveltwoBean> leveltwo;

        public String getLeveloneid() {
            return leveloneid;
        }

        public void setLeveloneid(String leveloneid) {
            this.leveloneid = leveloneid;
        }

        public String getLevelonedesc() {
            return levelonedesc;
        }

        public void setLevelonedesc(String levelonedesc) {
            this.levelonedesc = levelonedesc;
        }

        public List<LeveltwoBean> getLeveltwo() {
            return leveltwo;
        }

        public void setLeveltwo(List<LeveltwoBean> leveltwo) {
            this.leveltwo = leveltwo;
        }

        public static class LeveltwoBean {
            /**
             * leveltwoid : 101001
             * leveltwodesc : 叶菜类
             */

            private String leveltwoid;
            private String leveltwodesc;

            public String getLeveltwoid() {
                return leveltwoid;
            }

            public void setLeveltwoid(String leveltwoid) {
                this.leveltwoid = leveltwoid;
            }

            public String getLeveltwodesc() {
                return leveltwodesc;
            }

            public void setLeveltwodesc(String leveltwodesc) {
                this.leveltwodesc = leveltwodesc;
            }
        }
    }
}