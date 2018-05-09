package util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import model.Block;
import model.Transaction;
import security.CryptoUtil;

public class MineBlock {

	/**
	 * 挖矿
	 * @param blockchain 整个区块链
	 * @param txs 需记账交易记录
	 * @param address 矿工钱包地址
	 * @return
	 */
	public static void mineBlock(List<Block> blockchain, List<Transaction> txs, String address) {
		//加入系统奖励的交易，默认挖矿奖励10个比特币
		Transaction sysTx = new Transaction(CryptoUtil.UUID(), "", address, 10);
		txs.add(sysTx);
		//获取当前区块链里的最后一个区块
	    	Block latestBlock = blockchain.get(blockchain.size() - 1);
		//随机数
		int nonce = 1;
		String hash = "";
		while(true){
			hash = CryptoUtil.SHA256(latestBlock.getHash() + JSON.toJSONString(txs) + nonce);
			if (hash.startsWith("0000")) {
		        System.out.println("=====计算结果正确，计算次数为：" +nonce+ ",hash:" + hash);
		        break;
	        }
			nonce++;
			System.out.println("计算错误，hash:" + hash); 
		}
		
		//解出难题，可以构造新区块并加入进区块链里
		Block newBlock = new Block(latestBlock.getIndex() + 1, System.currentTimeMillis(), (ArrayList<Transaction>) txs, nonce, latestBlock.getHash(), hash);
		blockchain.add(newBlock);
		System.out.println("挖矿后的区块链：" + JSON.toJSONString(blockchain));
	}
	
	/**
	 * 查询余额
	 * @param blockchain
	 * @param address
	 * @return
	 */
	public static int getWalletBalance(List<Block> blockchain, String address) {
		int balance = 0;
		for (Block block : blockchain) {
	        	List<Transaction> transactions = block.getTransactions();
	        	for (Transaction transaction : transactions) {
	            		if (address.equals(transaction.getRecipient())) {
	            			balance += transaction.getAmount();
	            		}
	            		if (address.equals(transaction.getSender())) {
	            			balance -= transaction.getAmount();
	            		}
	        	}
	   	 }
		return balance;
	}
}
