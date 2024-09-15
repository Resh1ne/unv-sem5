SELECT suppliers.id, suppliers.name, suppliers.status, suppliers.city,
products.id, products.name, products.color, products.size, products.city,
projects.id, projects.name, projects.city
FROM quantity
JOIN suppliers ON quantity.supplier_id = suppliers.id
JOIN products ON quantity.product_id = products.id
JOIN projects ON quantity.project_id = projects.id


--16. Получить общее количество деталей Д1, поставляемых поставщиком П1
---------------------
--v1
SELECT products.name, suppliers.name FROM quantity
JOIN suppliers ON quantity.supplier_id = suppliers.id
JOIN products ON quantity.product_id = products.id
WHERE products.id = 1 AND suppliers.id = 1
--v2
SELECT COUNT(products.name) AS "кол-во деталей" FROM quantity
JOIN suppliers ON quantity.supplier_id = suppliers.id
JOIN products ON quantity.product_id = products.id
WHERE products.id = 1 AND suppliers.id = 1
---------------------
--27. Получить номера поставщиков, поставляющих деталь Д1 для некоторого проекта в количестве, большем среднего количества деталей Д1 в поставках для этого проекта
WITH avg_quantity AS (
    SELECT project_id, AVG(count) AS avg_count
    FROM quantity
    WHERE product_id = 1
    GROUP BY project_id
)
SELECT q.supplier_id
FROM quantity q
JOIN avg_quantity a ON q.project_id = a.project_id
WHERE q.product_id = 1
  AND q.count > a.avg_count
GROUP BY q.supplier_id
---------------------
--35. Получить пары "номер поставщика-номер детали", такие, что данный поставщик не поставляет  данную деталь
SELECT s.id AS supplier_id, p.id AS product_id
FROM suppliers s
CROSS JOIN products p
LEFT JOIN quantity q ON s.id = q.supplier_id AND p.id = q.product_id
WHERE q.supplier_id IS NULL
---------------------
--5. Получить все сочетания "цвета деталей-города деталей"
SELECT DISTINCT color, city
FROM products
---------------------
--8. Получить все такие тройки "номера поставщиков-номера деталей-номера проектов", для которых  никакие из двух выводимых поставщиков, деталей и проектов не размещены в одном городе
SELECT q.supplier_id, q.product_id, q.project_id
FROM quantity q
JOIN suppliers s ON q.supplier_id = s.id
JOIN products p ON q.product_id = p.id
JOIN projects pr ON q.project_id = pr.id
WHERE s.city != p.city
  AND p.city != pr.city
  AND s.city != pr.city
---------------------
--17. Для каждой детали, поставляемой для проекта, получить номер детали, номер проекта и  соответствующее общее количество
SELECT q.product_id, q.project_id, SUM(q.count) AS total_quantity
FROM quantity q
GROUP BY q.product_id, q.project_id
---------------------
--21. Получить номера деталей, поставляемых для какого-либо проекта в Лондоне
SELECT DISTINCT quantity.product_id FROM quantity
JOIN projects ON quantity.project_id = projects.id
WHERE projects.city = 'Москва'
---------------------
--12. Получить номера деталей, поставляемых для всех проектов, обеспечиваемых поставщиком из  того же города, где размещен проект
WITH supplier_project AS (
    SELECT q.product_id, q.project_id
    FROM quantity q
    JOIN suppliers s ON q.supplier_id = s.id
    JOIN projects p ON q.project_id = p.id
    WHERE s.city = p.city
),
project_count AS (
    SELECT project_id, COUNT(DISTINCT product_id) AS num_products
    FROM supplier_project
    GROUP BY project_id
),
detail_count AS (
    SELECT product_id, COUNT(DISTINCT project_id) AS num_projects
    FROM supplier_project
    GROUP BY product_id
),
project_total AS (
    SELECT COUNT(DISTINCT project_id) AS total_projects
    FROM project_count
)
SELECT dc.product_id
FROM detail_count dc
JOIN project_total pt ON dc.num_projects = pt.total_projects
---------------------
--29. Получить номера проектов, полностью обеспечиваемых поставщиком П1
SELECT q1.project_id
FROM quantity q1
WHERE q1.supplier_id = 1
  AND NOT EXISTS (
      SELECT 1 FROM quantity q2
      WHERE q2.project_id = q1.project_id AND q2.supplier_id != 1
  )
GROUP BY q1.project_id
---------------------
--23. Получить номера поставщиков, поставляющих по крайней мере одну деталь, поставляемую по  крайней мере одним поставщиком, который поставляет по крайней мере одну красную деталь
WITH red_suppliers AS (
    SELECT DISTINCT q1.supplier_id
    FROM quantity q1
    JOIN products p1 ON q1.product_id = p1.id
    WHERE p1.color = 'Красный'
),
suppliers_with_red_products AS (
    SELECT DISTINCT q2.product_id
    FROM quantity q2
    JOIN red_suppliers rs ON q2.supplier_id = rs.supplier_id
),
result_suppliers AS (
    SELECT DISTINCT q3.supplier_id
    FROM quantity q3
    JOIN suppliers_with_red_products srp ON q3.product_id = srp.product_id
)
SELECT supplier_id
FROM result_suppliers
---------------------