package com.example.springboot;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

public final class Singleton {
    private static Singleton instance;

    private static final String CONTRACT_ADDRESS = "0xbe78c7713dfe64ba3ea01eccd7b3a48d04b81b78"; // TODO: change accordingly

    private static final String SOURCE = System.getenv("SOURCE");
    private static final String SOURCE_PASSWORD = System.getenv("SOURCE_PASSWORD");

    private NumberService contract;
    private final Web3j web = Web3j.build(new HttpService());

    private Singleton() throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(
                SOURCE_PASSWORD,
                SOURCE
        );

        this.contract = NumberService.load(
                CONTRACT_ADDRESS,
                this.getWeb(),
                credentials,
                new DefaultGasProvider()
        );
    }

    public NumberService getContract() {
        return this.contract;
    }

    public Web3j getWeb() {
        return this.web;
    }

    public static Singleton getInstance() throws Exception {
        if ( instance == null ) {
            instance = new Singleton();
        }
        return instance;
    }
}