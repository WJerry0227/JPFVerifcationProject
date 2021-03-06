package edu.ut.verify.example;

import edu.ut.verify.ProcessController;
import edu.ut.verify.core.statechart.StateChart;
import edu.ut.verify.core.StateMachine;
import edu.ut.verify.core.TestCase;
import edu.ut.verify.core.TestcaseGenerator;
import edu.ut.verify.statechart2java.XMIPaser;
import org.junit.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static edu.ut.verify.ProcessController.orderEvaluator;
import static edu.ut.verify.ProcessController.testCaseEvaluator;

/**
 * Created by Jerry Wang on 2018/11/28.
 */
public class VendingMachineImplTest {

    static List<Order> orders;
    Order order;
    ResultMsg resultMsg;

    @BeforeClass
    public static void initInputSet() throws Exception{
        // parse file
        String fileName = ProcessController.class.getClassLoader().getResource("VendingMachine.xmi").getFile();
        File file = new File(fileName);
        StateChart st = XMIPaser.parser(file);
        String dataInput = ProcessController.class.getClassLoader().getResource("DataInput.txt").getFile();
        st.parseDataInput(dataInput);


        // generate StateMachine Path
        StateMachine stateMachine = new StateMachine(st);
        TestcaseGenerator tg = new TestcaseGenerator(stateMachine);
        tg.testGenerate();

        orders = new ArrayList<>();

        tg.getCaseList().stream().filter(TestCase::isValid).forEach(e->
            orders.add(new Order(e))
        );

        testCaseEvaluator(tg.getCaseList());

        System.out.println();
        System.out.println();
        System.out.println(" ********************************* After Invalid Filter *********************************** ");
        System.out.println();
        System.out.println();

        orderEvaluator(orders);

    }


    @Test
    public void noInputDataTest(){
        resultMsg = test(0);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
        Assert.assertEquals(resultMsg.getReturnMoney(), order.getReturnMoney());
    }

    @Test
    public void notEnoughDrinkTest(){
        resultMsg = test(1);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
    }

    @Test
    public void noChangeDispense(){
        resultMsg = test(2);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
        Assert.assertEquals(resultMsg.getReturnMoney(), order.getReturnMoney());
    }

    @Test
    public void notEnoughMoneyNotVerify(){
        resultMsg = test(3);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
        Assert.assertEquals(resultMsg.getReturnMoney(), order.getReturnMoney());
    }


    @Test
    public void notVerifyAmountPurchase(){
        resultMsg = test(4);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
        Assert.assertEquals(resultMsg.getReturnMoney(), order.getReturnMoney());
    }

    @Test
    public void verifyAmountNoChangePurchase(){
        resultMsg = test(5);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
        Assert.assertEquals(resultMsg.getReturnMoney(), order.getReturnMoney());
    }

    @Test
    public void notEnoughMoneyVerify(){
        resultMsg = test(6);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
        Assert.assertEquals(resultMsg.getReturnMoney(), order.getReturnMoney());
    }

    @Test
    public void regularPurchaseVerifyAmountChange(){
        resultMsg = test(7);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
        Assert.assertEquals(resultMsg.getReturnMoney(), order.getReturnMoney());
    }

    @Test
    public void availableNoChangeNoVerify(){
        resultMsg = test(8);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
        Assert.assertEquals(resultMsg.getReturnMoney(), order.getReturnMoney());
    }

    @Test
    public void availableNoVerifyNotEnoughMoney(){
        resultMsg = test(9);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
        Assert.assertEquals(resultMsg.getReturnMoney(), order.getReturnMoney());
    }

    @Test
    public void availableNoVerifyChangePurchase(){
        resultMsg = test(10);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
        Assert.assertEquals(resultMsg.getReturnMoney(), order.getReturnMoney());
    }

    @Test
    public void availableVerifyNoChangePurchase(){
        resultMsg = test(11);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
        Assert.assertEquals(resultMsg.getReturnMoney(), order.getReturnMoney());
    }

    @Test
    public void availableVerifyNotEnoughMoney(){
        resultMsg = test(12);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
        Assert.assertEquals(resultMsg.getReturnMoney(), order.getReturnMoney());
    }

    @Test
    public void availableVerifyEnoughPurchase(){
        resultMsg = test(13);
        Assert.assertEquals(resultMsg.getPathStatus().toString(),order.getSequence().toString());
        Assert.assertEquals(resultMsg.getReturnMoney(), order.getReturnMoney());
    }

    private ResultMsg test(int index){
        order = orders.get(index);
        VendingMachineService vendingMachineService = new VendingMachineImpl();
        return vendingMachineService.purchasing(order);
    }

    @After
    public void printPath(){
        System.out.println("===========================================================================================");
        System.out.println("PARAMETERS    : " + order);
        System.out.println("ACTUAL PATH   : "+resultMsg.getPathStatus());
        System.out.println("EXPECTED PATH : " + order.getSequence().toString());
    }
}
