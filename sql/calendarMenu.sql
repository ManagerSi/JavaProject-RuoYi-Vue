-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('日历', '3', '1', 'calendar', 'system/calendar/index', 1, 0, 'C', '0', '0', 'system:calendar:list', '#', 'admin', sysdate(), '', null, '日历菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('日历查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:calendar:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('日历新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:calendar:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('日历修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:calendar:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('日历删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:calendar:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('日历导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:calendar:export',       '#', 'admin', sysdate(), '', null, '');