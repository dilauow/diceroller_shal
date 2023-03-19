package com.example.diceroller

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation

import com.example.diceroller.databinding.FragmentPlayGameBinding
import com.google.android.material.color.utilities.Score
import kotlin.random.Random


class playGameFragment : Fragment() {

    private lateinit var playerdicesList: MutableList<ImageView>
    private lateinit var computerdicesList: MutableList<ImageView>
    private lateinit var binding: FragmentPlayGameBinding

    // shuffle scoreholders
    private var playershuffleScore: Int = 0
    private var computershuffleScore: Int = 0



    //    shuffle counter
    var throwCounter = 0

    //    temporary player score holder for each throw
    private var playerEachThrowScore = 0
    private var computerEachThrowScore = 0

    //previous throw value holder
    var previousDiceValueArr: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5);
    var computerDicespreviousValueArr: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5);

    var computerShuffleCounter = 0
    var playerDicesRollability: MutableList<Boolean> = mutableListOf(true, true, true, true, true)
    var computerDicesRollability: MutableList<Boolean> = mutableListOf(true, true, true, true, true)


    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_play_game, container, false)

        //    update the all wins text
        binding.allWins.setText("H:" + additionalClasses.playerWins.toString() + "/ C:" + additionalClasses.computerWins.toString())

//        TODO added the initial counter
        computerShuffleCounter = (Random.nextInt(3) + 1)
        // player dices list
        playerdicesList =
            mutableListOf(binding.pd1, binding.pd2, binding.pd3, binding.pd4, binding.pd5)

//        call eventListeners
        addEventListnersToDices()
        //when throw button pressed
        binding.shuffle.setOnClickListener {
            throwCounter++
            // TODO: this code change to throw the computer dices too
            if (throwCounter == 1) {
                computerEachThrowScore = throwDices(computerdicesList, false)

            }

            // checking 2nd throw
            if (throwCounter < 3) {
                playerEachThrowScore = throwDices(playerdicesList, true)


                Log.d("check throw count", throwCounter.toString())
                //in the 3rd throw ,3rd throw score should be added
            } else if (throwCounter == 3) {
                playerEachThrowScore = throwDices(playerdicesList, player = true)
//                computershuffleScore += throwDices(computerdicesList, player = false)
                playershuffleScore += playerEachThrowScore
//                updateScores(playershuffleScore, computershuffleScore)
                computerShuffleCounter= computerRoleAndUpdateScores(computerEachThrowScore)
                checkingWinner(it)
                throwCounter = 0
                Log.d("check throw count", throwCounter.toString())
            }

        }

        //computer dices list
        computerdicesList =
            mutableListOf(binding.cd1, binding.cd2, binding.cd3, binding.cd4, binding.cd5)

        //when score button pressed
        binding.Score.setOnClickListener {
            if (throwCounter != 0) {
                playershuffleScore += playerEachThrowScore
//                computershuffleScore += throwDices(computerdicesList, player = false)
//                updateScores(playershuffleScore, computershuffleScore)
                computerShuffleCounter= computerRoleAndUpdateScores(computerEachThrowScore)
                checkingWinner(it)
                throwCounter = 0

            } else {
                Toast.makeText(activity, "Press Throw Button First!!", Toast.LENGTH_SHORT).show();
            }
        }

        return binding.root
    }


    private fun rollDice(imgR: ImageView): Int {
        val imageNumber = (Random.nextInt(6) + 1);
        val imageResource = when (imageNumber) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        imgR.setImageResource(imageResource)
        return imageNumber

    }

    private fun throwDices(array: MutableList<ImageView>, player: Boolean): Int {
        var total = 0
        var count = 0
        if (player) {
            throwCounter += 0
        }
//shuffle all the dices
        for (img in array) {
            if (player) {
                img.alpha = 1f
//get the total of throw
                if (playerDicesRollability[count]) {
                    val value = rollDice(img)
                    previousDiceValueArr[count] = value
                    total += value

                } else {
                    total += previousDiceValueArr[count]
                    playerDicesRollability[count] = true

                }
            } else {
                if (computerDicesRollability[count]) {
                    val value = rollDice(img)
                    computerDicespreviousValueArr[count] = value
                    total += value
                } else {
                    total += computerDicespreviousValueArr[count]
                    computerDicesRollability[count] = true
                }

            }
            count++
        }

        return total

    }

    //updating scores
    private fun updateScores(playerScore: Int, computerScore: Int) {
        binding.yourScroreValue.setText(playerScore.toString())
        binding.computerScoreValue.setText(computerScore.toString())

    }

    //checking winner
    private fun checkingWinner(view: View) {
        if (playershuffleScore >= 101 || computershuffleScore >= 101) {
            if (playershuffleScore < computershuffleScore) {
                additionalClasses.computerWins += 1
                // player lost
                val popUp = WonLostDialog.newInstance("You Lost",false)
                popUp.show(childFragmentManager,"dialog")
            } else if (computershuffleScore < playershuffleScore) {
                // player wins
                additionalClasses.playerWins += 1
                val popUp = WonLostDialog.newInstance("You Win",true)
                popUp.show(childFragmentManager,"dialog")


            }
        }
    }

    private fun changeAlpha(imgR: ImageView) {

        imgR.alpha = 0.4f
    }

    //    add event listeners
    private fun addEventListnersToDices() {
        Log.d("eventlistener", throwCounter.toString())
        for (img in playerdicesList) {
            img.setOnClickListener {
                if (throwCounter != 0) {
                    Log.d("Clicked", it.id.toString())
                    val clickedId = it.id  //from this id we are generating id(index) for each dice
                    val arrayIndex = when (clickedId) {
                        2131231063 -> 0
                        2131231064 -> 1
                        2131231065 -> 2
                        2131231066 -> 3
                        else -> 4
                    }
                    changeAlpha(playerdicesList[arrayIndex]) //alpha used to show the dice is  in object dice array
                    playerDicesRollability[arrayIndex] = false
                } else {
                    Toast.makeText(activity, "Press Throw Button First!!", Toast.LENGTH_SHORT)
                        .show();
                }

            }
        }

    }

    //    TODO random stratergies
    private fun computerRoleAndUpdateScores(firstAttemptScore: Int): Int {

        Log.d("Role NUmber", computerShuffleCounter.toString())
        if (computerShuffleCounter == 1) {
            computershuffleScore += firstAttemptScore
            updateScores(playershuffleScore, computershuffleScore )
        }
        if (computerShuffleCounter == 2) {

            computerDicesRollability =
                additionalClasses.makeHighestValuesFalse(computerDicespreviousValueArr)
            Log.d("Changed List ", computerDicesRollability.toString())
            val tempCompScore = throwDices(computerdicesList, false)
            computershuffleScore += tempCompScore
            updateScores(playershuffleScore, computershuffleScore )
        }
        if (computerShuffleCounter == 3) {

            computerDicesRollability =
                additionalClasses.makeHighestValuesFalse(computerDicespreviousValueArr)
            Log.d("Changed List ", computerDicesRollability.toString())
            var tempCompScore = throwDices(computerdicesList, false)

            computerDicesRollability =
                additionalClasses.makeHighestValuesFalse(computerDicespreviousValueArr)
            Log.d("Changed List ", computerDicesRollability.toString())
            tempCompScore = throwDices(computerdicesList, false)
            computershuffleScore += tempCompScore
            updateScores(playershuffleScore, computershuffleScore)

        }

        return (Random.nextInt(3) + 1)

    }


}