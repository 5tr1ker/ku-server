package com.team.saver.market.store.repository;

import com.team.saver.market.store.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long>, CustomMenuRepository {
}
