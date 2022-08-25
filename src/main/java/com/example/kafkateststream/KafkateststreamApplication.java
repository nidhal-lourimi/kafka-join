package com.example.kafkateststream;

import com.example.kafkateststream.model.Customer;
import com.example.kafkateststream.model.Purchase;
import com.example.kafkateststream.serdes.SerdesFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Properties;


@Slf4j
@SpringBootApplication
public class KafkateststreamApplication {
    private static KafkaStreams kafkaStreams;

    public static void main(String[] args) throws IOException, InterruptedException {
        stop();
        StreamsBuilder streamBuilder = new StreamsBuilder();
        /*StreamsBuilder streamBuilder = new StreamsBuilder();*/
        Properties props = new Properties();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-purchase-analysis21111");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");



        /*        KStream<String, CustomerPurchase> orderStream = streamBuilder.stream(Serdes.String(), SerdesFactory.serdFrom(Order.class), "orders");*/
        KTable<String, Customer> customerTable = streamBuilder.table("customers", Consumed.with(Serdes.String(), SerdesFactory.serdFrom(Customer.class)), Materialized.as("customer-state-store")/*"customer-state-store"*/);
        KTable<String, Purchase> purchaseTable = streamBuilder.table("purchase", Consumed.with(Serdes.String(), SerdesFactory.serdFrom(Purchase.class)), Materialized.as("purchase-state-store"));
//		itemTable.toStream().foreach((String itemName, Item item) -> System.out.printf("Item info %s-%s-%s-%s\n", item.getItemName(), item.getAddress(), item.getType(), item.getPrice()));
        KTable<String, CustomerPurchase> kTable = ((KTable<String, Customer>) customerTable)
                .join(purchaseTable, (Customer customer, Purchase purchase) -> CustomerPurchase.fromall(customer, purchase));
   /*             .filter((String userName, OrderUser orderUser) -> orderUser.userAddress != null)
                .map((String userName, OrderUser orderUser) -> new KeyValue<String, OrderUser>(orderUser.itemName, orderUser))
                .through(Serdes.String(), SerdesFactory.serdFrom(OrderUser.class), (String key, OrderUser orderUser, int numPartitions) -> (orderUser.getItemName().hashCode() & 0x7FFFFFFF) % numPartitions, "orderuser-repartition-by-item")
                .leftJoin(itemTable, (OrderUser orderUser, Item item) -> OrderUserItem.fromOrderUser(orderUser, item), Serdes.String(), SerdesFactory.serdFrom(OrderUser.class))
                .filter((String item, OrderUserItem orderUserItem) -> StringUtils.compare(orderUserItem.userAddress, orderUserItem.itemAddress) == 0)
//				.foreach((String itemName, OrderUserItem orderUserItem) -> System.out.printf("%s-%s-%s-%s\n", itemName, orderUserItem.itemAddress, orderUserItem.userName, orderUserItem.userAddress))
                .map((String item, OrderUserItem orderUserItem) -> KeyValue.<String, Double>pair(orderUserItem.gender, (Double)(orderUserItem.quantity * orderUserItem.itemPrice)))
                .groupByKey(Serdes.String(), Serdes.Double())
                .reduce((Double v1, Double v2) -> v1 + v2, "gender-amount-state-store");*/
//		kTable.foreach((str, dou) -> System.out.printf("%s-%s\n", str, dou));
        kTable
                .toStream().peek((k, v) -> {
                    log.info("key :" + k + "object :" + v.toString());
                }).to("my-kafka-stream-stream-left-join-out", Produced.with(Serdes.String(),SerdesFactory.serdFrom(CustomerPurchase.class)));
/*                .map((String gender, Double total) -> new KeyValue<String, String>(gender, String.valueOf(total)))
                .to("gender-amount");*/
        final Topology topology = streamBuilder.build();
        KafkaStreams kafkaStreams = new KafkaStreams(topology, props);
        kafkaStreams.cleanUp();
        kafkaStreams.start();

     /*   System.in.read();
        kafkaStreams.close();
        kafkaStreams.cleanUp();*/
    }


    public static class CustomerPurchase /*implements Serde<CustomerPurchase>*/ {

        private Long customerId;
        private String name;
        private String email;
        private Long purchaseId;
        private String itemName;
        private Integer itemsNumber;


        public static CustomerPurchase fromCustomer(Customer customer) {
            CustomerPurchase customerPurchase = new CustomerPurchase();
            if (customer == null) {
                return customerPurchase;
            }
            customerPurchase.customerId = customer.id;
            customerPurchase.name = customer.getName();
            customerPurchase.email = customer.getEmail();
            customerPurchase.purchaseId = customer.getPurchaseId();
            return customerPurchase;
        }

        public static CustomerPurchase fromall(Customer customer, Purchase purchase) {
            CustomerPurchase customerPurchase = fromCustomer(customer);
            if (purchase == null) {
                return customerPurchase;
            }
            customerPurchase.itemName = purchase.getItemName();
            customerPurchase.itemsNumber = purchase.getItemsNumber();
            return customerPurchase;
        }


    }

    private static void stop() {
        if (kafkaStreams != null) {
            kafkaStreams.close();
        }

    }

}