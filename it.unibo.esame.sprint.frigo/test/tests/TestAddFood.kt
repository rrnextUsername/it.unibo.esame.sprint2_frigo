package tests

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlinx.coroutines.GlobalScope
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.launch
import it.unibo.kactor.sysUtil
import org.junit.jupiter.api.AfterEach
import it.unibo.kactor.MsgUtil

class TestAddFood {
  var resource : ActorBasic? = null
	
	@BeforeEach
	fun systemSetUp() {
  	 		GlobalScope.launch{ //activate an observer ...
 				itunibo.coap.observer.main()		//blocking
 			}	
  	 		GlobalScope.launch{
 			    println(" %%%%%%% TestFridge starts fridge mind ")
				it.unibo.ctxFridge.main()
 			}
			delay(5000)		//give the time to start
			resource = sysUtil.getActor("fridge")	
		    println(" %%%%%%% TestFridge getActors resource=${resource}")
 	}
 
	@AfterEach
	fun terminate() {
		println(" %%%%%%% TestFridge terminate ")
	}
 
	@Test
	fun addFoodTest() {
		println(" %%%%%%% TestFridge  addFoodTest ")
		println("ciaoooooooo")
		sendMessageToFridge(resource!!,"pasta",1000)
		solveCheckGoal(resource!!,"presenza(pasta, cibo, frigo)")
 	}
//----------------------------------------
	
	fun sendMessageToFridge( actor : ActorBasic, name : String, time : Long ){
		actor.scope.launch{
			println("--- sendMessageToFridge changeInventory(aggiungi,cibo,$name)")
  			MsgUtil.sendMsg("changeInventory","changeInventory(aggiungi,cibo,$name)",actor)
 		}
		delay(time) //give time to do the move
  	}

 	
	fun solveCheckGoal( actor : ActorBasic, goal : String ){
		actor.solve( goal  )
		var result =  actor.resVar
		println(" %%%%%%%  actor={$actor.name} goal= $goal  result = $result")
		assertTrue(result=="success","%%%%%%% TestFridge expected 'success' found $result")
	}

	fun delay( time : Long ){
		Thread.sleep( time )
	}


}
