/***********************************************
 * Filename        : DishAction.java 
 * Copyright      : Copyright (c) 2014
 * Company        : Innovaee
 * Created        : 11/27/2014
 ************************************************/

package com.innovaee.eorder.action.dish;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.innovaee.eorder.action.BaseAction;
import com.innovaee.eorder.entity.Category;
import com.innovaee.eorder.entity.Dish;
import com.innovaee.eorder.exception.CategoryNotFoundException;
import com.innovaee.eorder.exception.DishNotFoundException;
import com.innovaee.eorder.exception.DuplicateNameException;
import com.innovaee.eorder.exception.InvalidPageSizeException;
import com.innovaee.eorder.exception.PageIndexOutOfBoundExcpeiton;
import com.innovaee.eorder.service.CategoryService;
import com.innovaee.eorder.service.DishService;
import com.innovaee.eorder.utils.Constants;
import com.innovaee.eorder.utils.MenuUtil;
import com.innovaee.eorder.utils.MessageUtil;
import com.innovaee.eorder.utils.StringUtil;
import com.innovaee.eorder.vo.CategoryVO;
import com.innovaee.eorder.vo.DishVO;
import com.innovaee.eorder.vo.EOrderUserDetailVO;
import com.innovaee.eorder.vo.MenuLinkVO;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

/**
 * @Title: DishAction
 * @Description: 功能Action（查询和删除）
 *
 * @version V1.0
 */
@Conversion(conversions = { @TypeConversion(key = "dish.price", converter = "com.innovaee.eorder.utils.FloatConverter") })
public class DishAction extends BaseAction {

    /** 对象序列化ID */
    private static final long serialVersionUID = -5184755551662453454L;

    /** 数据库中对应的功能描述常量 */
    public static final String FUNCTION_DESC = "Dish";

    /** 菜品名称 */
    private DishVO dish;

    /** 分类id */
    private Long categoryId;

    /** 菜品分类列表 */
    private List<Category> categoryList;

    /** 菜品分类列表 */
    private List<CategoryVO> categoryVOList;

    /** 功能值对象列表 */
    private List<DishVO> dishvos = new ArrayList<DishVO>();

    /** 功能服务类对象 */
    @Resource
    private DishService dishService;

    /** 功能服务类对象 */
    @Resource
    private CategoryService categoryService;

    /**
     * 进入菜品页面
     * 
     * @return
     */
    public String dish() {
        dish = new DishVO();

        renewCategoryVOList();

        // 刷新系统菜单
        refreshPageData();
        return SUCCESS;
    }

    /**
     * 进入菜品页面
     * 
     * @return
     */
    public String list() {
        dish = new DishVO();

        renewCategoryVOList();

        getDishList();
        // 刷新系统菜单
        refreshPageData();
        return SUCCESS;
    }

    /**
     * 新增页面
     * 
     * @return
     */
    public String add() {
        // 刷新系统菜单
        refreshPageData();

        renewCategoryVOList();

        dish = new DishVO();

        if (null == dish.getPicPath() || "".equals(dish.getPicPath())) {
            dish.setPicPath(Constants.DEFAULT_DISH_PIC);
        }

        return SUCCESS;
    }

    /**
     * 保存功能
     * 
     * @return
     */
    public String save() {
        try {
            if (!checkDishVO()) {
                return ERROR;
            } else {

                dish.setCategoryId(categoryId);
                // 新增成功
                dishService.addDish(dish);
                this.setMessage(MessageUtil.getMessage("dish_save_success",
                        dish.getName()));
                dish = new DishVO();
            }
        } catch (DuplicateNameException e) {
            this.setMessage(e.getMessage());
            return ERROR;
        } catch (CategoryNotFoundException e) {
            this.setMessage(e.getMessage());
            return ERROR;
        } finally {
            renewCategoryVOList();
            refreshPageData();
            getDishList();
        }

        return SUCCESS;
    }

    /**
     * 加载单个功能信息
     * 
     * @return
     */
    public String edit() {
        // 刷新系统菜单
        refreshPageData();
        //
        renewCategoryVOList();
        dish = new DishVO();
        try {
            if (null != id && !"".equals(id.trim())) {
                Dish dishDB = dishService.getDishById(Long.parseLong(id));
                dish.setId(dishDB.getId());
                dish.setName(dishDB.getName());
                dish.setPrice(dishDB.getPrice());
                dish.setPicPath(dishDB.getPicPath());
                dish.setOnSell(dishDB.isOnSell());
                dish.setName(dishDB.getName());
                dish.setCategoryId(dishDB.getCategory().getId());
            } else {
                this.setMessage("ERROR");
                return ERROR;
            }
        } catch (DishNotFoundException e) {
            this.setMessage(e.getMessage());
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * 更新功能
     * 
     * @return
     */
    public String update() {
        try {
            // 校验输入的值
            if (!checkDishVO()) {
                return ERROR;
            } else {
                dish.setCategoryId(categoryId);
                // 新增成功
                dishService.updateDish(dish);
                this.setMessage(MessageUtil.getMessage("dish_update_success",
                        dish.getName()));
                dish = new DishVO();
            }
        } catch (DuplicateNameException e) {
            this.setMessage(e.getMessage());
            return ERROR;
        } catch (CategoryNotFoundException e) {
            this.setMessage(e.getMessage());
            return ERROR;
        } catch (NumberFormatException e) {
            this.setMessage(e.getMessage());
            return ERROR;
        } catch (DishNotFoundException e) {
            this.setMessage(e.getMessage());
            return ERROR;
        } finally {
            renewCategoryVOList();
            refreshPageData();
            getDishList();
        }
        return SUCCESS;
    }

    /**
     * 删除功能
     * 
     * @return
     */
    public String remove() {
        // 更新页面数据
        refreshPageData();

        if (null != id) {
            try {
                dishService.deleteDish(Long.parseLong(id));
            } catch (DishNotFoundException e) {
                this.setMessage(e.getMessage());
                return ERROR;
            }
        }

        this.setMessage(MessageUtil.getMessage("delete_success"));

        // 更新记录列表
        getDishList();
        // 更新分类列表
        renewCategoryVOList();

        return SUCCESS;
    }

    /**
     * 刷新页面数据
     */
    private void refreshPageData() {
        // 当前用户的工具栏
        List<MenuLinkVO> toolbarList = MenuUtil.getToolbarLinkVOList();

        List<MenuLinkVO> menuLink = null;
        if (null != toolbarList && 0 < toolbarList.size()) {
            // 第一个功能对应的菜单
            menuLink = MenuUtil.getMenuLinkVOList(FUNCTION_DESC);
        }

        this.setToolbarList(toolbarList);
        this.setMenuList(menuLink);
        this.setCurrentFunctionDesc(FUNCTION_DESC);
        this.setCurrentToolbar(MenuUtil.getParentFunctionDesc(FUNCTION_DESC));

        EOrderUserDetailVO userDetail = (EOrderUserDetailVO) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        this.setLoginName(userDetail.getUser().getUsername());
    }

    /**
     * 刷新列表
     */
    private void getDishList() {
        Long id = 0L;
        try {
            Integer recordCount = dishService.getDishCountById(categoryId);
            this.setCount(recordCount);
            int pageTotal = 1;
            if (0 == recordCount % Constants.PAGE_SIZE) {
                pageTotal = recordCount / Constants.PAGE_SIZE;
            } else {
                pageTotal = recordCount / Constants.PAGE_SIZE + 1;
            }
            this.setPageTotal(pageTotal);

            // 处理用户点击【上一页】和【下一页】边界情况
            if (pageNow > pageTotal) {
                pageNow = pageTotal;
                pageInput = pageNow;
            } else if (pageNow < 1) {
                pageNow = 1;
                pageInput = 1;
            }

            List<Dish> dishes = null;
            if (null != pageInput) {
                if (pageInput > pageTotal) {
                    pageInput = pageTotal;
                    pageNow = pageTotal;
                } else if (pageInput < 1) {
                    pageNow = 1;
                    pageInput = 1;
                }

                dishes = dishService.getDishesByPage(pageInput,
                        Constants.PAGE_SIZE, categoryId);

                pageNow = pageInput;
            } else {
                dishes = dishService.getDishesByPage(pageNow,
                        Constants.PAGE_SIZE, categoryId);
            }

            DishVO dishvo = null;
            Category category = null;
            for (Dish dish : dishes) {
                dishvo = new DishVO();
                // BeanUtils.copyProperties(dish, dishvo);
                dishvo.setId(dish.getId());
                dishvo.setName(dish.getName());
                dishvo.setPicPath(dish.getPicPath());
                dishvo.setPrice(dish.getPrice());
                id = dish.getCategory().getId();
                category = categoryService.getCategoryById(id);
                if (null != category) {
                    dishvo.setCategoryId(category.getId());
                    // dishvo.setName(category.getName());
                }
                dishvos.add(dishvo);
            }
        } catch (PageIndexOutOfBoundExcpeiton e) {
            this.setMessage(e.getMessage());
        } catch (InvalidPageSizeException e) {
            this.setMessage(e.getMessage());
        } catch (CategoryNotFoundException e) {
            this.setMessage(e.getMessage());
        }
    }

    /**
     * 
     */
    private void renewCategoryVOList() {
        List<CategoryVO> categoryVOList = new ArrayList<CategoryVO>();
        // 更新权限列表
        CategoryVO categoryVO = null;
        List<Category> categoryList = categoryService.getAllCategories();
        for (Category category : categoryList) {
            categoryVO = new CategoryVO();
            BeanUtils.copyProperties(category, categoryVO);
            categoryVO.setStringId(category.getId().toString());
            categoryVOList.add(categoryVO);
        }
        this.setCategoryVOList(categoryVOList);
    }

    /**
     * 检查一个菜品的设定是否合法
     */
    private boolean checkDishVO() {
        boolean isValidVO = true;

        if (null == dish) {
            isValidVO = false;
            addFieldError("dish.dishvo",
                    MessageUtil.getMessage("dish_invalid_vo"));
            return isValidVO;
        }

        if (dish.getPrice() < 1f || dish.getPrice() > 100000f) {
            isValidVO = false;
            addFieldError("dish.price",
                    MessageUtil.getMessage("dish_price_rule"));
        }

        if (StringUtil.isEmpty(dish.getName())) {
            isValidVO = false;
            addFieldError("dish.name", MessageUtil.getMessage("dish_name_rule"));
        }

        if (StringUtil.isEmpty(dish.getPicPath())) {
            isValidVO = false;
            addFieldError("dish.pic.path",
                    MessageUtil.getMessage("pic_path_rule"));
        }

        return isValidVO;
    }

    public List<DishVO> getDishvos() {
        return dishvos;
    }

    public void setDishvos(List<DishVO> dishvos) {
        this.dishvos = dishvos;
    }

    public DishService getDishService() {
        return dishService;
    }

    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    public DishVO getDish() {
        return dish;
    }

    public void setDish(DishVO dish) {
        this.dish = dish;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<CategoryVO> getCategoryVOList() {
        return categoryVOList;
    }

    public void setCategoryVOList(List<CategoryVO> categoryVOList) {
        this.categoryVOList = categoryVOList;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

}