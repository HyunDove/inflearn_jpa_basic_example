package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

public class JpaMain {

    public static void main(String[] args) {

        // 하나만 생성해서 Application 전체에서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 쓰레드간 공유 X ( 사용하고 버려야 한다.)
        EntityManager em = emf.createEntityManager();

        // JPA의 모든 데이터 변경은 Transaction 안에서 실행
        EntityTransaction tx = em.getTransaction(); // DB Connection Get

        tx.begin(); // // Transaction 시작

        try {
            
            Order order = new Order();
            em.persist(order);

            // order.addOrderItem(new OrderItem());
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            em.persist(orderItem);

            tx.commit(); // Commit
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // Connection 반환
            emf.close();
        }
    }
}
