package com.nyzc.gdm.currencyratio.Bean;

import java.io.Serializable;
import java.util.List;

public class Rooms implements Serializable {


    /**
     * id : 0
     * jsonrpc : 2.0
     * result : {"id":"1.15.22","house_id":"1.14.9","owner":"1.2.105","label":["SEER"],"description":"SEER明天中午能到1毛吗？（以AEX明天中午12点整的价格为准）","script":"","room_type":0,"status":"opening","option":{"result_owner_percent":9000,"reward_per_oracle":0,"accept_asset":"1.3.0","minimum":100000,"maximum":"100000000000","start":"2018-07-01T13:55:52","stop":"2018-07-01T14:55:52","input_duration_secs":360,"filter":{"reputation":0,"guaranty":0,"volume":0},"allowed_oracles":[],"allowed_countries":[],"allowed_authentications":[]},"running_option":{"room_type":0,"selection_description":["能","不能"],"range":2,"lmsr":{"L":"100000000000"},"participators":[[{"player":"1.2.106","when":"2018-07-01T13:56:42","amount":"100000000000","paid":"62011450696","sequence":0},{"player":"1.2.106","when":"2018-07-01T13:56:48","amount":"100000000000","paid":"81366632353","sequence":1},{"player":"1.2.106","when":"2018-07-01T13:56:54","amount":"10000000000","paid":"8859151213","sequence":2},{"player":"1.2.106","when":"2018-07-01T13:59:30","amount":"10000000000","paid":"8754125276","sequence":5},{"player":"1.2.106","when":"2018-07-01T14:00:00","amount":"70000000000","paid":"55452434187","sequence":7},{"player":"1.2.106","when":"2018-07-01T14:00:09","amount":-100000000000,"paid":-76459998050,"sequence":8},{"player":"1.2.106","when":"2018-07-01T14:00:18","amount":-10000000000,"paid":-6569809840,"sequence":9},{"player":"1.2.106","when":"2018-07-01T14:00:24","amount":-10000000000,"paid":-6341096630,"sequence":10},{"player":"1.2.106","when":"2018-07-01T14:00:30","amount":-10000000000,"paid":-6106173179,"sequence":11},{"player":"1.2.106","when":"2018-07-01T14:00:30","amount":-10000000000,"paid":-5866000793,"sequence":12},{"player":"1.2.106","when":"2018-07-01T14:00:33","amount":-10000000000,"paid":-5621637508,"sequence":13},{"player":"1.2.106","when":"2018-07-01T14:00:39","amount":-10000000000,"paid":-5374220931,"sequence":14},{"player":"1.2.106","when":"2018-07-01T14:00:42","amount":-10000000000,"paid":-5124947952,"sequence":15}],[{"player":"1.2.106","when":"2018-07-01T13:57:00","amount":"10000000000","paid":1140848787,"sequence":3},{"player":"1.2.106","when":"2018-07-01T13:57:06","amount":"10000000000","paid":1245874724,"sequence":4},{"player":"1.2.106","when":"2018-07-01T13:59:45","amount":"100000000000","paid":"18633367647","sequence":6},{"player":"1.2.106","when":"2018-07-01T14:00:48","amount":-10000000000,"paid":-4875052048,"sequence":16}]],"total_shares":"184439666007","settled_balance":0,"settled_row":-1,"settled_column":-1,"player_count":[13,4],"total_player_count":17,"lmsr_running":{"items":["120000000000","110000000000"],"accelerator":[]},"guaranty_alone":0},"owner_result":[],"final_result":[],"committee_result":[],"oracle_sets":[],"final_finished":false,"settle_finished":false,"last_deal_time":"1970-01-01T00:00:00"}
     */

    private int id;
    private String jsonrpc;
    private ResultBean result;

    @Override
    public String toString() {
        return "Rooms{" +
                "id=" + id +
                ", jsonrpc='" + jsonrpc + '\'' +
                ", result=" + result +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * id : 1.15.22
         * house_id : 1.14.9
         * owner : 1.2.105
         * label : ["SEER"]
         * description : SEER明天中午能到1毛吗？（以AEX明天中午12点整的价格为准）
         * script :
         * room_type : 0
         * status : opening
         * option : {"result_owner_percent":9000,"reward_per_oracle":0,"accept_asset":"1.3.0","minimum":100000,"maximum":"100000000000","start":"2018-07-01T13:55:52","stop":"2018-07-01T14:55:52","input_duration_secs":360,"filter":{"reputation":0,"guaranty":0,"volume":0},"allowed_oracles":[],"allowed_countries":[],"allowed_authentications":[]}
         * running_option : {"room_type":0,"selection_description":["能","不能"],"range":2,"lmsr":{"L":"100000000000"},"participators":[[{"player":"1.2.106","when":"2018-07-01T13:56:42","amount":"100000000000","paid":"62011450696","sequence":0},{"player":"1.2.106","when":"2018-07-01T13:56:48","amount":"100000000000","paid":"81366632353","sequence":1},{"player":"1.2.106","when":"2018-07-01T13:56:54","amount":"10000000000","paid":"8859151213","sequence":2},{"player":"1.2.106","when":"2018-07-01T13:59:30","amount":"10000000000","paid":"8754125276","sequence":5},{"player":"1.2.106","when":"2018-07-01T14:00:00","amount":"70000000000","paid":"55452434187","sequence":7},{"player":"1.2.106","when":"2018-07-01T14:00:09","amount":-100000000000,"paid":-76459998050,"sequence":8},{"player":"1.2.106","when":"2018-07-01T14:00:18","amount":-10000000000,"paid":-6569809840,"sequence":9},{"player":"1.2.106","when":"2018-07-01T14:00:24","amount":-10000000000,"paid":-6341096630,"sequence":10},{"player":"1.2.106","when":"2018-07-01T14:00:30","amount":-10000000000,"paid":-6106173179,"sequence":11},{"player":"1.2.106","when":"2018-07-01T14:00:30","amount":-10000000000,"paid":-5866000793,"sequence":12},{"player":"1.2.106","when":"2018-07-01T14:00:33","amount":-10000000000,"paid":-5621637508,"sequence":13},{"player":"1.2.106","when":"2018-07-01T14:00:39","amount":-10000000000,"paid":-5374220931,"sequence":14},{"player":"1.2.106","when":"2018-07-01T14:00:42","amount":-10000000000,"paid":-5124947952,"sequence":15}],[{"player":"1.2.106","when":"2018-07-01T13:57:00","amount":"10000000000","paid":1140848787,"sequence":3},{"player":"1.2.106","when":"2018-07-01T13:57:06","amount":"10000000000","paid":1245874724,"sequence":4},{"player":"1.2.106","when":"2018-07-01T13:59:45","amount":"100000000000","paid":"18633367647","sequence":6},{"player":"1.2.106","when":"2018-07-01T14:00:48","amount":-10000000000,"paid":-4875052048,"sequence":16}]],"total_shares":"184439666007","settled_balance":0,"settled_row":-1,"settled_column":-1,"player_count":[13,4],"total_player_count":17,"lmsr_running":{"items":["120000000000","110000000000"],"accelerator":[]},"guaranty_alone":0}
         * owner_result : []
         * final_result : []
         * committee_result : []
         * oracle_sets : []
         * final_finished : false
         * settle_finished : false
         * last_deal_time : 1970-01-01T00:00:00
         */

        private String id;
        private String house_id;
        private String owner;
        private String description;
        private String script;
        private int room_type;
        private String status;
        private OptionBean option;
        private RunningOptionBean running_option;
        private boolean final_finished;
        private boolean settle_finished;
        private String last_deal_time;
        private List<String> label;
        private List<?> owner_result;
        private List<?> final_result;
        private List<?> committee_result;
        private List<?> oracle_sets;

        @Override
        public String toString() {
            return "ResultBean{" +
                    "id='" + id + '\'' +
                    ", house_id='" + house_id + '\'' +
                    ", owner='" + owner + '\'' +
                    ", description='" + description + '\'' +
                    ", script='" + script + '\'' +
                    ", room_type=" + room_type +
                    ", status='" + status + '\'' +
                    ", option=" + option +
                    ", running_option=" + running_option +
                    ", final_finished=" + final_finished +
                    ", settle_finished=" + settle_finished +
                    ", last_deal_time='" + last_deal_time + '\'' +
                    ", label=" + label +
                    ", owner_result=" + owner_result +
                    ", final_result=" + final_result +
                    ", committee_result=" + committee_result +
                    ", oracle_sets=" + oracle_sets +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHouse_id() {
            return house_id;
        }

        public void setHouse_id(String house_id) {
            this.house_id = house_id;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getScript() {
            return script;
        }

        public void setScript(String script) {
            this.script = script;
        }

        public int getRoom_type() {
            return room_type;
        }

        public void setRoom_type(int room_type) {
            this.room_type = room_type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public OptionBean getOption() {
            return option;
        }

        public void setOption(OptionBean option) {
            this.option = option;
        }

        public RunningOptionBean getRunning_option() {
            return running_option;
        }

        public void setRunning_option(RunningOptionBean running_option) {
            this.running_option = running_option;
        }

        public boolean isFinal_finished() {
            return final_finished;
        }

        public void setFinal_finished(boolean final_finished) {
            this.final_finished = final_finished;
        }

        public boolean isSettle_finished() {
            return settle_finished;
        }

        public void setSettle_finished(boolean settle_finished) {
            this.settle_finished = settle_finished;
        }

        public String getLast_deal_time() {
            return last_deal_time;
        }

        public void setLast_deal_time(String last_deal_time) {
            this.last_deal_time = last_deal_time;
        }

        public List<String> getLabel() {
            return label;
        }

        public void setLabel(List<String> label) {
            this.label = label;
        }

        public List<?> getOwner_result() {
            return owner_result;
        }

        public void setOwner_result(List<?> owner_result) {
            this.owner_result = owner_result;
        }

        public List<?> getFinal_result() {
            return final_result;
        }

        public void setFinal_result(List<?> final_result) {
            this.final_result = final_result;
        }

        public List<?> getCommittee_result() {
            return committee_result;
        }

        public void setCommittee_result(List<?> committee_result) {
            this.committee_result = committee_result;
        }

        public List<?> getOracle_sets() {
            return oracle_sets;
        }

        public void setOracle_sets(List<?> oracle_sets) {
            this.oracle_sets = oracle_sets;
        }

        public static class OptionBean implements Serializable{
            /**
             * result_owner_percent : 9000
             * reward_per_oracle : 0
             * accept_asset : 1.3.0
             * minimum : 100000
             * maximum : 100000000000
             * start : 2018-07-01T13:55:52
             * stop : 2018-07-01T14:55:52
             * input_duration_secs : 360
             * filter : {"reputation":0,"guaranty":0,"volume":0}
             * allowed_oracles : []
             * allowed_countries : []
             * allowed_authentications : []
             */

            private int result_owner_percent;
            private int reward_per_oracle;
            private String accept_asset;
            private int minimum;
            private String maximum;
            private String start;
            private String stop;
            private int input_duration_secs;
            private FilterBean filter;
            private List<?> allowed_oracles;
            private List<?> allowed_countries;
            private List<?> allowed_authentications;

            public int getResult_owner_percent() {
                return result_owner_percent;
            }

            public void setResult_owner_percent(int result_owner_percent) {
                this.result_owner_percent = result_owner_percent;
            }

            public int getReward_per_oracle() {
                return reward_per_oracle;
            }

            public void setReward_per_oracle(int reward_per_oracle) {
                this.reward_per_oracle = reward_per_oracle;
            }

            public String getAccept_asset() {
                return accept_asset;
            }

            public void setAccept_asset(String accept_asset) {
                this.accept_asset = accept_asset;
            }

            public int getMinimum() {
                return minimum;
            }

            public void setMinimum(int minimum) {
                this.minimum = minimum;
            }

            public String getMaximum() {
                return maximum;
            }

            public void setMaximum(String maximum) {
                this.maximum = maximum;
            }

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getStop() {
                return stop;
            }

            public void setStop(String stop) {
                this.stop = stop;
            }

            public int getInput_duration_secs() {
                return input_duration_secs;
            }

            public void setInput_duration_secs(int input_duration_secs) {
                this.input_duration_secs = input_duration_secs;
            }

            public FilterBean getFilter() {
                return filter;
            }

            public void setFilter(FilterBean filter) {
                this.filter = filter;
            }

            public List<?> getAllowed_oracles() {
                return allowed_oracles;
            }

            public void setAllowed_oracles(List<?> allowed_oracles) {
                this.allowed_oracles = allowed_oracles;
            }

            public List<?> getAllowed_countries() {
                return allowed_countries;
            }

            public void setAllowed_countries(List<?> allowed_countries) {
                this.allowed_countries = allowed_countries;
            }

            public List<?> getAllowed_authentications() {
                return allowed_authentications;
            }

            public void setAllowed_authentications(List<?> allowed_authentications) {
                this.allowed_authentications = allowed_authentications;
            }

            public static class FilterBean implements Serializable{
                /**
                 * reputation : 0
                 * guaranty : 0
                 * volume : 0
                 */

                private int reputation;
                private int guaranty;
                private int volume;

                public int getReputation() {
                    return reputation;
                }

                public void setReputation(int reputation) {
                    this.reputation = reputation;
                }

                public int getGuaranty() {
                    return guaranty;
                }

                public void setGuaranty(int guaranty) {
                    this.guaranty = guaranty;
                }

                public int getVolume() {
                    return volume;
                }

                public void setVolume(int volume) {
                    this.volume = volume;
                }
            }
        }

        public static class RunningOptionBean implements Serializable{
            /**
             * room_type : 0
             * selection_description : ["能","不能"]
             * range : 2
             * lmsr : {"L":"100000000000"}
             * participators : [[{"player":"1.2.106","when":"2018-07-01T13:56:42","amount":"100000000000","paid":"62011450696","sequence":0},{"player":"1.2.106","when":"2018-07-01T13:56:48","amount":"100000000000","paid":"81366632353","sequence":1},{"player":"1.2.106","when":"2018-07-01T13:56:54","amount":"10000000000","paid":"8859151213","sequence":2},{"player":"1.2.106","when":"2018-07-01T13:59:30","amount":"10000000000","paid":"8754125276","sequence":5},{"player":"1.2.106","when":"2018-07-01T14:00:00","amount":"70000000000","paid":"55452434187","sequence":7},{"player":"1.2.106","when":"2018-07-01T14:00:09","amount":-100000000000,"paid":-76459998050,"sequence":8},{"player":"1.2.106","when":"2018-07-01T14:00:18","amount":-10000000000,"paid":-6569809840,"sequence":9},{"player":"1.2.106","when":"2018-07-01T14:00:24","amount":-10000000000,"paid":-6341096630,"sequence":10},{"player":"1.2.106","when":"2018-07-01T14:00:30","amount":-10000000000,"paid":-6106173179,"sequence":11},{"player":"1.2.106","when":"2018-07-01T14:00:30","amount":-10000000000,"paid":-5866000793,"sequence":12},{"player":"1.2.106","when":"2018-07-01T14:00:33","amount":-10000000000,"paid":-5621637508,"sequence":13},{"player":"1.2.106","when":"2018-07-01T14:00:39","amount":-10000000000,"paid":-5374220931,"sequence":14},{"player":"1.2.106","when":"2018-07-01T14:00:42","amount":-10000000000,"paid":-5124947952,"sequence":15}],[{"player":"1.2.106","when":"2018-07-01T13:57:00","amount":"10000000000","paid":1140848787,"sequence":3},{"player":"1.2.106","when":"2018-07-01T13:57:06","amount":"10000000000","paid":1245874724,"sequence":4},{"player":"1.2.106","when":"2018-07-01T13:59:45","amount":"100000000000","paid":"18633367647","sequence":6},{"player":"1.2.106","when":"2018-07-01T14:00:48","amount":-10000000000,"paid":-4875052048,"sequence":16}]]
             * total_shares : 184439666007
             * settled_balance : 0
             * settled_row : -1
             * settled_column : -1
             * player_count : [13,4]
             * total_player_count : 17
             * lmsr_running : {"items":["120000000000","110000000000"],"accelerator":[]}
             * guaranty_alone : 0
             */

            private int room_type;
            private int range;
            private LmsrBean lmsr;
            private String total_shares;
            private int settled_balance;
            private int settled_row;
            private int settled_column;
            private int total_player_count;
            private LmsrRunningBean lmsr_running;
            private int guaranty_alone;
            private List<String> selection_description;
            private List<List<ParticipatorsBean>> participators;
            private List<Integer> player_count;

            private AdvancedRunningBean advanced_running;
            private PVPRunningBean pvp_running;
            private AdvancedBean advanced;

            public AdvancedRunningBean getAdvanced_running() {
                return advanced_running;
            }

            public void setAdvanced_running(AdvancedRunningBean advanced_running) {
                this.advanced_running = advanced_running;
            }

            public PVPRunningBean getPvp_running() {
                return pvp_running;
            }

            public void setPvp_running(PVPRunningBean pvp_running) {
                this.pvp_running = pvp_running;
            }

            public AdvancedBean getAdvanced() {
                return advanced;
            }

            public void setAdvanced(AdvancedBean advanced) {
                this.advanced = advanced;
            }

            public static class AdvancedBean implements Serializable {
                /**
                 * pool : 500000000000
                 * awards : [23000,39000,23000]
                 */

                private String pool;
                private List<Integer> awards;

                public String getPool() {
                    return pool;
                }

                public void setPool(String pool) {
                    this.pool = pool;
                }

                public List<Integer> getAwards() {
                    return awards;
                }

                public void setAwards(List<Integer> awards) {
                    this.awards = awards;
                }
            }

            public static class AdvancedRunningBean implements Serializable {
                private List<List<Integer>> total_participate;

                public List<List<Integer>> getTotal_participate() {
                    return total_participate;
                }

                public void setTotal_participate(List<List<Integer>> total_participate) {
                    this.total_participate = total_participate;
                }
            }
            public static class PVPRunningBean implements Serializable {
                private List<Integer> total_participate;

                public List<Integer> getTotal_participate() {
                    return total_participate;
                }

                public void setTotal_participate(List<Integer> total_participate) {
                    this.total_participate = total_participate;
                }
            }


            public int getRoom_type() {
                return room_type;
            }

            public void setRoom_type(int room_type) {
                this.room_type = room_type;
            }

            public int getRange() {
                return range;
            }

            public void setRange(int range) {
                this.range = range;
            }

            public LmsrBean getLmsr() {
                return lmsr;
            }

            public void setLmsr(LmsrBean lmsr) {
                this.lmsr = lmsr;
            }

            public String getTotal_shares() {
                return total_shares;
            }

            public void setTotal_shares(String total_shares) {
                this.total_shares = total_shares;
            }

            public int getSettled_balance() {
                return settled_balance;
            }

            public void setSettled_balance(int settled_balance) {
                this.settled_balance = settled_balance;
            }

            public int getSettled_row() {
                return settled_row;
            }

            public void setSettled_row(int settled_row) {
                this.settled_row = settled_row;
            }

            public int getSettled_column() {
                return settled_column;
            }

            public void setSettled_column(int settled_column) {
                this.settled_column = settled_column;
            }

            public int getTotal_player_count() {
                return total_player_count;
            }

            public void setTotal_player_count(int total_player_count) {
                this.total_player_count = total_player_count;
            }

            public LmsrRunningBean getLmsr_running() {
                return lmsr_running;
            }

            public void setLmsr_running(LmsrRunningBean lmsr_running) {
                this.lmsr_running = lmsr_running;
            }

            public int getGuaranty_alone() {
                return guaranty_alone;
            }

            public void setGuaranty_alone(int guaranty_alone) {
                this.guaranty_alone = guaranty_alone;
            }

            public List<String> getSelection_description() {
                return selection_description;
            }

            public void setSelection_description(List<String> selection_description) {
                this.selection_description = selection_description;
            }

            public List<List<ParticipatorsBean>> getParticipators() {
                return participators;
            }

            public void setParticipators(List<List<ParticipatorsBean>> participators) {
                this.participators = participators;
            }

            public List<Integer> getPlayer_count() {
                return player_count;
            }

            public void setPlayer_count(List<Integer> player_count) {
                this.player_count = player_count;
            }

            public static class LmsrBean implements Serializable{
                /**
                 * L : 100000000000
                 */

                private String L;

                public String getL() {
                    return L;
                }

                public void setL(String L) {
                    this.L = L;
                }
            }

            public static class LmsrRunningBean implements Serializable{
                private List<String> items;
                private List<?> accelerator;

                public List<String> getItems() {
                    return items;
                }

                public void setItems(List<String> items) {
                    this.items = items;
                }

                public List<?> getAccelerator() {
                    return accelerator;
                }

                public void setAccelerator(List<?> accelerator) {
                    this.accelerator = accelerator;
                }
            }

            public static class ParticipatorsBean implements Serializable{
                /**
                 * player : 1.2.106
                 * when : 2018-07-01T13:56:42
                 * amount : 100000000000
                 * paid : 62011450696
                 * sequence : 0
                 */

                private String player;
                private String when;
                private String amount;
                private String paid;
                private int sequence;

                public String getPlayer() {
                    return player;
                }

                public void setPlayer(String player) {
                    this.player = player;
                }

                public String getWhen() {
                    return when;
                }

                public void setWhen(String when) {
                    this.when = when;
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getPaid() {
                    return paid;
                }

                public void setPaid(String paid) {
                    this.paid = paid;
                }

                public int getSequence() {
                    return sequence;
                }

                public void setSequence(int sequence) {
                    this.sequence = sequence;
                }
            }
        }
    }
}
