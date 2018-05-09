package main;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import model.Block;
import model.Transaction;
import security.CryptoUtil;
import util.MineBlock;

public class BitCoinStart {

	public static void main(String[] args) {
		//创建一个空的区块链
		List<Block> blockchain = new ArrayList<>();
		//生成创世区块
		Block block = new Block(1, System.currentTimeMillis(), new ArrayList<Transaction>(), 1, "1", "1");
		//加入创世区块到区块链里
		blockchain.add(block);
		System.out.println(JSON.toJSONString(blockchain));
		// 发送方钱包地址
		String sender = "sender_wallet";
		//接收方钱包地址
		String recipient = "recipient_wallet";
		
		//创建一个空的交易集合
		List<Transaction> txs = new ArrayList<>();
		//挖矿
		MineBlock.mineBlock(blockchain, txs, sender);
		System.out.println(sender + "钱包的余额为：" + MineBlock.getWalletBalance(blockchain, sender));
		
		//创建一个空的交易集合
		List<Transaction> txs1 = new ArrayList<>();
		//已发生但未记账的交易记录，发送者给接收者转账3个比特币
		Transaction tx1 = new Transaction(CryptoUtil.UUID(), sender, recipient, 3);
		//已发生但未记账的交易记录，发送者给接收者转账1个比特币
		Transaction tx2 = new Transaction(CryptoUtil.UUID(), sender, recipient, 1);
		txs1.add(tx1);
		txs1.add(tx2);
		//挖矿
		MineBlock.mineBlock(blockchain, txs1, sender);
		System.out.println(sender + "钱包的余额为：" + MineBlock.getWalletBalance(blockchain, sender));
		System.out.println(recipient + "钱包的余额为：" + MineBlock.getWalletBalance(blockchain, recipient));
	}
}
