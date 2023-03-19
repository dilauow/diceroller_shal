package com.example.diceroller

class additionalClasses {

    companion object{
        @JvmStatic
        public var playerWins =0
        @JvmStatic
        public var computerWins =0

        @JvmStatic
        public var playersCurrentScore = 0
        @JvmStatic
        public var computerCurrentScore =0


        public fun makeHighestValuesFalse(list: MutableList<Int>): MutableList<Boolean>{
            val rollaBility = mutableListOf<Boolean>(true,true,true,true,true,)
            val maxScore = list.max()
            for(i in 0..4){
                if(list[i] == maxScore){
                    rollaBility[i] = false
                }
            }
            return rollaBility
        }
    }

}