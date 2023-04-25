package cart.repository;

import cart.model.Item;
import cart.repository.dto.ItemDto;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ItemDao {

    private final JdbcTemplate jdbcTemplate;

    public ItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(Item item) {
        String sql = "INSERT INTO ITEM(name, image_url, price) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"item_id"});
            ps.setString(1, item.getName());
            ps.setString(2, item.getImageUrl());
            ps.setInt(3, item.getPrice());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public ItemDto findById(Long id) {
        String sql = "SELECT item_id, name, image_url, price FROM ITEM WHERE item_id = ?";

        return jdbcTemplate.queryForObject(sql, actorRowMapper, id);
    }

    public List<ItemDto> findAll() {
        String sql = "SELECT item_id, name, image_url, price FROM ITEM";

        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public void update(Long id, Item item) {
        String sql = "UPDATE ITEM SET name = ?, image_url = ?, price = ? WHERE item_id = ?";

        jdbcTemplate.update(sql, item.getName(), item.getImageUrl(), item.getPrice(), id);
    }

    public int delete(Long id) {
        String sql = "DELETE FROM ITEM WHERE item_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private final RowMapper<ItemDto> actorRowMapper = (resultSet, rowNum) -> {
        ItemDto itemDto = new ItemDto(
                resultSet.getLong("item_id"),
                resultSet.getString("name"),
                resultSet.getString("image_url"),
                resultSet.getInt("price")
        );
        return itemDto;
    };
}