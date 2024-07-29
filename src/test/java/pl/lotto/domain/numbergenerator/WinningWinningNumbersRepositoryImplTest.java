package pl.lotto.domain.numbergenerator;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class WinningWinningNumbersRepositoryImplTest implements IWinningNumbersRepository {
    Map<String, WinningNumbers> winningNumbers = new ConcurrentHashMap<>();

    @Override
    public <S extends WinningNumbers> S save(S entity) {
        winningNumbers.put(entity.getDrawDate().toString(), entity);
        return entity;
    }

    @Override
    public Optional<WinningNumbers> findByDrawDate(LocalDateTime drawDate) {
        return winningNumbers.values().stream()
                .filter(winningNumbers -> winningNumbers.getDrawDate().equals(drawDate))
                .findFirst();
    }

    @Override
    public boolean existsByDrawDate(LocalDateTime nextDrawDate) {
        return winningNumbers.values().stream()
                .anyMatch(winningNumbers -> winningNumbers.getDrawDate().equals(nextDrawDate));
    }

    @Override
    public <S extends WinningNumbers> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends WinningNumbers> List<S> insert(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends WinningNumbers> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends WinningNumbers> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends WinningNumbers> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends WinningNumbers> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends WinningNumbers> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends WinningNumbers> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends WinningNumbers, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends WinningNumbers> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<WinningNumbers> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<WinningNumbers> findAll() {
        return List.of();
    }

    @Override
    public List<WinningNumbers> findAllById(Iterable<String> strings) {
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
    public void delete(WinningNumbers entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends WinningNumbers> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<WinningNumbers> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<WinningNumbers> findAll(Pageable pageable) {
        return null;
    }
}
