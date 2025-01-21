<template>
    <div class="admin-container">
      <el-card class="admin-card">
        <div  class="admin-header">
          <h2>管理者ダッシュボード</h2>
        </div>
  
        <el-tabs v-model="activeTab">
          <el-tab-pane label="注文管理" name="orders">
            <div class="orders-section">
              <el-table :data="orders" style="width: 100%" align="center">
                <el-table-column prop="orderId" label="注文番号" width="100" />
                <el-table-column prop="userId" label="お客様番号" width="120" />
                <el-table-column prop="petId" label="ペットID" width="120" />
                <!-- <el-table-column prop="petName" label="ペット名" width="120" /> -->
                <!-- <el-table-column prop="policyName" label="保険プラン" width="150" /> -->
                <el-table-column prop="orderStatus" label="状態" width="120">
                  <template #default="scope">
                    <el-tag :type="getOrderStatusType(scope.row.orderStatus)">
                      {{ getOrderStatusText(scope.row.orderStatus) }}
                    </el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="createTime" label="注文日" width="180" />
                <el-table-column prop="endDate" label="有効期限" width="180" />
                 <el-table-column prop="price" label="金額" width="120">
                  <template #default="scope">
                    ¥{{ scope.row.totalPrice }}
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="200">
                  <template #default="scope">
                    <el-button
                      size="small"
                      type="primary"
                      @click="handleEdit(scope.row)"
                    >
                      状態更新
                    </el-button>
                    <el-button
                      size="small"
                      @click="showOrderDetails(scope.row)"
                    >
                      詳細
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
  
              <div class="pagination-container">
              <el-pagination
                @current-change="handleCurrentChange"
                @size-change="handleSizeChange"
                :current-page="currentPage"
                :page-size="pageSize"
                :total="total"
                :page-sizes="[10, 20, 50, 100]"
                layout="total, sizes, prev, pager, next"
              />
            </div>
            </div>
          </el-tab-pane>
  
          <el-tab-pane label="保険プラン管理" name="plans">
            <div class="plans-section">
              <div class="plans-header">
                <el-button type="primary" @click="showAddPlanDialog">
                  新規プラン追加
                </el-button>
              </div>
  
              <el-table :data="insurancePlans" style="width: 100%">
                <el-table-column prop="policyId" label="プランID" width="120" />
                <el-table-column prop="policyName" label="プラン名" width="150" />
                <el-table-column prop="premium" label="保険料" width="120">
                  <template #default="scope">
                    ¥{{ scope.row.premium }}
                  </template>
                </el-table-column>
                <el-table-column prop="coverage" label="補償額" width="120">
                  <template #default="scope">
                    ¥{{ scope.row.coverage }}
                  </template>
                </el-table-column>
                <el-table-column prop="termMonths" label="期間(月)" width="120" />
                <el-table-column prop="description" label="説明" />
                <el-table-column label="操作" width="200">
                  <template #default="scope">
                    <el-button
                      size="small"
                      type="primary"
                      @click="handleEditPlan(scope.row)"
                    >
                      編集
                    </el-button>
                    <el-button
                      size="small"
                      type="danger"
                      @click="confirmDeletePlan(scope.row)"
                    >
                      削除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>
        </el-tabs>
  
        <!-- Order Status Update Dialog -->

        <el-dialog
          title="注文状態の更新"
          v-model="orderStatusDialog"
          width="400px"
        >
          <div class="status-options">
            <el-radio-group v-model="selectedStatus">
              <el-radio-button label="pending">審査中</el-radio-button>
              <el-radio-button label="active">完了</el-radio-button>
              <el-radio-button label="expired">期限切れ</el-radio-button>
              <el-radio-button label="cancelled">取り消し</el-radio-button>
            </el-radio-group>
          </div>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="orderStatusDialog = false">キャンセル</el-button>
              <el-button type="primary" @click="updateOrderStatus">
                確認
              </el-button>
            </span>
          </template>
        </el-dialog>
  
        <!-- Insurance Plan Dialog -->
        <el-dialog
          :title="editingPlan ? 'プランを編集する' : '新規プラン追加'"
          v-model="planDialog"
          width="500px"
        >
          <el-form :model="planForm" label-width="120px">
            <el-form-item label="プラン名">
              <el-input v-model="planForm.policyName" />
            </el-form-item>
            <el-form-item label="保険料">
              <el-input-number 
                v-model="planForm.premium"
                :min="0"
                :step="1000"
              />
            </el-form-item>
            <el-form-item label="補償額">
              <el-input-number
                v-model="planForm.coverage"
                :min="0"
                :step="10000"
              />
            </el-form-item>
            <el-form-item label="期間(月)">
              <el-input-number
                v-model="planForm.termMonths"
                :min="1"
                :max="24"
              />
            </el-form-item>
            <el-form-item label="説明">
              <el-input
                type="textarea"
                v-model="planForm.description"
                :rows="3"
              />
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="planDialog = false">キャンセル</el-button>
              <el-button type="primary" @click="savePlan">
                保存
              </el-button>
            </span>
          </template>
        </el-dialog>
  
        <!-- Order Details Dialog -->
        <el-dialog
          title="注文詳細"
          v-model="orderDetailsDialog"
          width="500px"
        >
          <div v-if="selectedOrder" class="order-details">
            <div class="detail-item">
              <span class="label">注文番号：</span>
              <span>{{ selectedOrder.orderId }}</span>
            </div>
            <div class="detail-item">
              <span class="label">お客様番号：</span>
              <span>{{ selectedOrder.userId }}</span>
            </div>
            <div class="detail-item">
              <span class="label">ペット名：</span>
              <span>{{ selectedOrder.petName }}</span>
            </div>
            <div class="detail-item">
              <span class="label">保険プラン：</span>
              <span>{{ selectedOrder.policyName }}</span>
            </div>
            <div class="detail-item">
              <span class="label">補償額：</span>
              <span>¥{{ selectedOrder.coverage }}</span>
            </div>
            <div class="detail-item">
              <span class="label">状態：</span>
              <el-tag :type="getOrderStatusType(selectedOrder.orderStatus)">
                {{ getOrderStatusText(selectedOrder.orderStatus) }}
              </el-tag>
            </div>
            <div class="detail-item">
              <span class="label">注文日：</span>
              <span>{{ selectedOrder.startDate }}</span>
            </div>
            <div class="detail-item">
              <span class="label">有効期限：</span>
              <span>{{ selectedOrder.endDate }}</span>
            </div>
            <div class="detail-item">
              <span class="label">金額：</span>
              <span>¥{{ selectedOrder.price }}</span>
            </div>
            <div class="detail-item">
              <span class="label">補償額：</span>
              <span>¥{{ selectedOrder.coverage }}</span>
            </div>
          </div>
        </el-dialog>
      </el-card>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import {  ElMessage,  ElTable, ElTableColumn, ElDialog, ElInput, ElButton,ElMessageBox } from 'element-plus';
  
  export default {
    components: {
    ElTable,
    ElTableColumn,
    ElDialog,
    ElInput,
    ElButton
  },
    name: 'AdminDashboard',
    setup() {
      // Reactive data
      const activeTab = ref('orders');
      const loading = ref(false);
      const currentPage = ref(1);
      const pageSize = ref(10);
      const total = ref(0);
      const orders = ref([]);
      const insurancePlans = ref([]);
      const orderStatusDialog = ref(false); // Reactive dialog
      const planDialog = ref(false); // Reactive dialog
      const orderDetailsDialog = ref(false);
      
      const selectedOrder = ref(null);
      const selectedStatus = ref('');
      const editingPlan = ref(null);
      
      const planForm = ref({
        policyName: '',
        premium: 0,
        coverage: 0,
        termMonths: 12,
        description: ''
      });
  
      // Fetch data methods
      const fetchOrders = async () => {
        try {
          const token = localStorage.getItem('token') || sessionStorage.getItem('token');
          loading.value = true;
          const response = await axios.get('http://localhost:8081/orders/admin', {
            params: {
              page: currentPage.value ,
              size: pageSize.value
            },
            headers: {
              Authorization: `Bearer ${token}`
            }
          });
          if (response.data.code === 200) {
            const data = response.data.data;
            orders.value = data.records;
            total.value = data.total;
            console.log(data);
          } else {
            this.$message.error(response.data.message || 'Failed to fetch orders');
          }
        } catch (error) {
          console.error(error);
        } finally {
          loading.value = false;
        }
      };
  
      const fetchPolicies = async () => {
        try {
          const token = localStorage.getItem('token') || sessionStorage.getItem('token');
          loading.value = true;
          const response = await axios.get('http://localhost:8081/policy', {
            headers: {
              Authorization: `Bearer ${token}`
            }
          });
          if (response.data.code === 200) {
            insurancePlans.value = response.data.data;
          } else {
            this.$message.error(response.data.message || 'Failed to fetch insurance policies');
          }
        } catch (error) {
          console.error(error);
        } finally {
          loading.value = false;
        }
      };

      const updateOrderStatus = async () => {
  const token = localStorage.getItem('token') || sessionStorage.getItem('token');
  console.log(token);
  if (!selectedOrder.value || !selectedStatus.value) return;
  try {
    // 发送请求
    const response = await axios.put(
      `http://localhost:8081/orders/admin/${selectedOrder.value.orderId}/status`,null,
      {
        headers: {
          Authorization: `${token}`
        },
        params: { status: selectedStatus.value }
       
      }
    );
   
    
    // 判断响应结果
    if (response.data.code === 200) {
      // 使用 ElMessage 显示成功消息
      ElMessage.success('注文状況が正常に更新されました');
      orderStatusDialog.value = false;
      await fetchOrders(); // 刷新订单列表
    } else {
      ElMessage.error(response.data.message || '注文状況の更新に失敗しました');
    }
  } catch (error) {
    console.error(error);
    ElMessage.error('注文状況の更新に失敗しました');
  }
};


const savePlan = async () => {
  const token = localStorage.getItem('token') || sessionStorage.getItem('token');
  try {
    let response;
    if (editingPlan.value) {
      // Update existing plan
      response = await axios.put(
        `http://localhost:8081/policy/${editingPlan.value.policyId}`,
        planForm.value,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
    } else {
      // Add new plan
      response = await axios.post('http://localhost:8081/policy', planForm.value, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
    }

    if (response.data.code === 200) {
        ElMessage.success(editingPlan.value ? '保険プランが正常に更新されました' : '新しい保険プランが正常に追加されました');
      planDialog.value = false; // Close the dialog
      await fetchPolicies(); // Refresh the insurance plans
    } else {
        ElMessage.error(response.data.message || '操作に失敗しました');
    }
  } catch (error) {
    console.error(error);
    ElMessage.error('操作に失敗しました');
  }
};
  
const deletePolicy = async (policyId) => {
  const token = localStorage.getItem('token') || sessionStorage.getItem('token'); // 获取token
  try {
    const response = await axios.delete(`http://localhost:8081/policy/${policyId}`, {
      headers: {
        Authorization: `Bearer ${token}` // 设置Authorization头
      }
    });

    if (response.data.code === 200) {
        ElMessage.success('保険プランが正常に削除されました'); // 修改为日文
      await fetchPolicies(); // 保険プランを更新
    } else {
      ElMessage.error(response.data.message || '保険プランの削除に失敗しました'); // 修改为日文
    }
  } catch (error) {
    console.error(error);
    ElMessage.error('エラーが発生しました。再度お試しください'); // 日文错误信息
  }
};
  
      // Event handlers
      const handleCurrentChange = (val) => {
        currentPage.value = val;
        fetchOrders();
      };
  
      const handleSizeChange = (val) => {
        pageSize.value = val;
        currentPage.value = 1;
        fetchOrders();
      };
      const confirmDeletePlan = (plan) => {
  ElMessageBox.confirm(
    'このプランを削除してもよろしいですか？',
    '確認',
    {
      confirmButtonText: '確認',
      cancelButtonText: 'キャンセル',
      type: 'warning',
    }
  )
    .then(() => {
      deletePolicy(plan.policyId); // プランを削除する関数を呼び出す
    })
    .catch(() => {
      console.log('削除操作がキャンセルされました'); // ユーザーがキャンセルした場合
    });
};
  
      // Status utilities
      const getOrderStatusType = (status) => {
        const types = {
          Pending: 'warning',
          Active: 'success',
          Cancelled: 'danger'
        };
        return types[status] || 'info';
      };
  
      const getOrderStatusText = (status) => {
        const texts = {
          Pending: '審査中',
          Active: '完了',
          Cancelled: 'キャンセル済み',
          Expired:'期限切れ'
        };
        return texts[status] || status;
      };

      const showOrderDetails = async (order) => {
      try {
        const token = localStorage.getItem('token') || sessionStorage.getItem('token');
        const response = await axios.get(`http://localhost:8081/orders/${order.orderId}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        selectedOrder.value = response.data.data;
        console.log(selectedOrder.value);
        orderDetailsDialog.value = true;
      } catch (error) {
        // ElMessage.error('注文詳細の取得に失敗しました');
      }
    };
    const showAddPlanDialog =()=>{
      planDialog.value = true;
    };
    const handleEdit = (order) => {
        // This method is for editing an order or policy
        if (order) {
          selectedOrder.value = order;
          // If editing an order, you might populate fields related to that order
          orderStatusDialog.value = true;
        } else {
          editingPlan.value = {}; // Clear any previous plan data
          planForm.value = { policyName: '', premium: 0, coverage: 0, termMonths: 12, description: '' };
          planDialog.value = true; // Open the insurance plan dialog for a new plan
        }
      };

      const handleEditPlan = (row) => {
  // 将选中的行数据赋值给编辑中的计划
  editingPlan.value = { ...row };

  // 将选中的行数据填充到表单中
  planForm.value = {
    policyName: row.policyName,
    policyType: row.policyType,
    premium: row.premium,
    coverage: row.coverage,
    description: row.description,
  };

  // 打开编辑对话框
  planDialog.value = true;
};
  
      // On component mount
      onMounted(() => {
        fetchOrders();
        fetchPolicies();
      });
  
      // Return to template
      return {
        activeTab,
        loading,
        currentPage,
        pageSize,
        total,
        orders,
        insurancePlans,
        orderStatusDialog,
        planDialog,
        orderDetailsDialog,
        selectedOrder,
        selectedStatus,
        editingPlan,
        planForm,
        fetchOrders,
        fetchPolicies,
        updateOrderStatus,
        savePlan,
        deletePolicy,
        handleCurrentChange,
        handleSizeChange,
        confirmDeletePlan,
        getOrderStatusType,
        getOrderStatusText,
        showOrderDetails,
        handleEdit,
        showAddPlanDialog,
        handleEditPlan
      };
    }
  };
  </script>
  
  <style scoped>
  .admin-container {
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
  }
  
  .admin-card {
    margin-bottom: 20px;
  }
  
  .admin-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .plans-header {
    margin-bottom: 20px;
  }
  
  .pagination-container {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }
  
  .status-options {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
  }
  
  .order-details .detail-item {
    margin-bottom: 15px;
    display: flex;
    align-items: center;
  }
  
  .order-details .label {
    width: 120px;
    color: #606266;
  }
  
  .el-table {
    margin-top: 20px;
  }
  </style>