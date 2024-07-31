package pl.lotto.domain.resultchecker;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class PlayerRepositoryTestImpl implements IPlayerRepository {
    Map<String, Player> players = new HashMap<>();

    @Override
    public <S extends Player> List<S> saveAll(Iterable<S> entities) {
        for (S entity : entities) {
            players.put(entity.getHash(), entity);
        }

        return (List<S>) entities;
    }

    @Override
    public Optional<Player> findById(String s) {
        return Optional.ofNullable(players.get(s));
    }

    @Override
    public <S extends Player> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Player> List<S> insert(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends Player> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Player> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Player> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Player> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Player> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Player> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Player, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Player> S save(S entity) {
        return null;
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Player> findAll() {
        return List.of();
    }

    @Override
    public List<Player> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Player entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Player> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Player> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Player> findAll(Pageable pageable) {
        return null;
    }
}
