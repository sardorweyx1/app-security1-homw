package uz.weyx.appsecurity1homw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.weyx.appsecurity1homw.entity.Pc;

@Repository
public interface PcRepository extends JpaRepository<Pc,Integer> {
}
