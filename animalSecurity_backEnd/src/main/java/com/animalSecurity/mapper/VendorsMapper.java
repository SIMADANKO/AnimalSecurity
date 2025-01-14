package com.animalSecurity.mapper;
import com.animalSecurity.entity.Users;
import com.animalSecurity.entity.Vendors;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 * 商家表 Mapper 接口
 * </p>
 *
 * @author lu
 * @since 2024-12-07
 */

public interface VendorsMapper extends BaseMapper<Vendors> {
    @Select("SELECT * FROM vendors WHERE vendor_name = #{vendorname}")
    Vendors selectByVendorname(String vendorname);
}
