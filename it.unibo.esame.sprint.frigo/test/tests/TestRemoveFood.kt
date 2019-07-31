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
  
class TestRemoveFood {
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
	fun removeFoodTest() {
		println(" %%%%%%% TestFridge  removeFoodTest ")
		sendMessageToFridge(resource!!,"torta",1000)
		solveCheckGoal(resource!!,"presenza(torta, cibo, frigo)")
 	}
//----------------------------------------
	
	fun sendMessageToFridge( actor : ActorBasic, name : String, time : Long ){
			println("--- sendMessageToFridge changeInventory(rimuovi,cibo,$name)")
		actor.scope.launch{
  			MsgUtil.sendMsg("changeInventory","changeInventory(rimuovi,cibo,$name)",actor)
 		}
		delay(time) //give time to do the move
  	}
 	
	fun solveCheckGoal( actor : ActorBasic, goal : String ){
		actor.solve( goal  )
		var result =  actor.resVar
		println(" %%%%%%%  actor={$actor.name} goal= $goal  result = $result")
		assertTrue(result=="fail","%%%%%%% TestFridge expected 'fail' found $result")
	}

	fun delay( time : Long ){
		Thread.sleep( time )
	}


}
