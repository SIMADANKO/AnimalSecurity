<template>
  <div id="app">
  

    <div class="container">
      <el-tabs v-model="activeTab" class="custom-tabs">
        <el-tab-pane label="私のペット" name="pets">
          <div class="page-header">
            <h2 class="section-title">私のペットリスト</h2>
            <el-button type="primary" @click="showAddPetDialog" class="add-pet-btn">
              <i class="el-icon-plus"></i> ペットを追加する
            </el-button>
          </div>

          <div class="pet-grid">
            <div v-for="pet in pets" :key="pet.petId" class="pet-card">
              <div class="pet-card-header">
                <div class="pet-avatar">
                  <i class="el-icon-pet"></i>
                </div>
                <h3 class="pet-name">{{ pet.petName }}</h3>
              </div>
              
              <div class="pet-info">
                <div class="info-item">
                  <span class="label">品種：</span>
                  <span class="value">{{ pet.breed }}</span>
                </div>
                <div class="info-item">
                  <span class="label">種類：</span>
                  <span class="value">{{ pet.species }}</span>
                </div>
                <div class="info-item">
                  <span class="label">性別：</span>
                  <span class="value">{{ pet.gender === 'Female' ? '母' : '公' }}</span>
                </div>
                <div class="info-item">
                  <span class="label">年齢：</span>
                  <span class="value">{{ pet.age }}歳</span>
                </div>
              </div>

              <div class="insurance-status">
                 <!-- 根据 pet.insuranceStatus 来判断保险状态，使用三元表达式 -->
  <div class="status-tag" :class="pet.insuranceStatus === 'active' ? 'active' : 'inactive'">
    {{ pet.insuranceStatus === 'active' ? '保険に加入済み' : '未加入保険' }}
  </div>
                <p class="expiry-date" v-if="pet.expiryDate">到期时间：{{ pet.expiryDate }}</p>
              </div>

              <div class="pet-actions">
  <!-- 如果保险状态是inactive或没有保险，则显示购买保险按钮 -->
  <el-button 
    type="primary" 
    class="action-btn"
    @click="buyInsurance(pet)" 
    v-if="pet.insuranceStatus !== 'active'">
    保険を購入する
  </el-button>

  <el-button 
                  type="warning" 
                  class="action-btn"
                  @click="showEditPetDialog(pet)">
                  編集
                </el-button>
                
                <el-button 
                  type="danger" 
                  class="action-btn"
                  @click="confirmDeletePet(pet)"
                  v-if="pet.insuranceStatus !== 'active'">
                  削除
                </el-button>
                <el-button 
                  type="success" 
                  class="action-btn"
                  @click="checkInsuranceDetail(pet)"
                  v-if="pet.insuranceStatus == 'active'">
                  保険プランを見る
                </el-button>

  <!-- 如果保险状态是active，则显示更新保险按钮 -->
  <!-- <el-button 
    type="success" 
    class="action-btn"
    @click="renewInsurance(pet)" 
    v-else>
    保険を更新する
  </el-button> -->

  
</div>
            </div>
          </div>
          
        </el-tab-pane>
        <el-tab-pane label="注文履歴" name="orders">
          <div class="page-header">
            <h2 class="section-title">注文履歴</h2>
          </div>
          
          <div class="orders-table">
            <el-table :data="orders" style="width: 100%" align="center" >
              <el-table-column prop="orderId" label="注文番号" width="100" />
                <el-table-column prop="userId" label="お客様番号" width="120" />
                <el-table-column prop="vendorId" label="管理者番号" width="120" />
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
              <el-table-column label="操作" width="120">
                <template #default="scope">
                  <el-button
                    size="small"
                    @click="showOrderDetails(scope.row)">
                    詳細
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            
            <div class="pagination">
              <el-pagination
                @current-change="handleOrderPageChange"
                :current-page="orderPage"
                :page-size="orderPageSize"
                :total="orderTotal"
                layout="prev, pager, next"
              />
            </div>
          </div>
        </el-tab-pane>

        <el-tab-pane label="保険プラン" name="plans">
          <div class="page-header">
            <h2 class="section-title">選択可能な保険プラン</h2>
          </div>

          <div class="plans-grid">
            <div v-for="plan in insurancePlans" :key="plan.policyId" class="plan-card">
              <div class="plan-header">
                <h3>{{ plan.policyName }}</h3>
                <div class="premium">¥{{ plan.premium }}</div>
              </div>
              
              <div class="plan-content">
                <p class="description">{{ plan.description || '无描述' }}</p>
                <div class="coverage">
                  <span class="coverage-label">保障範囲</span>
                  <span class="coverage-amount">¥{{ plan.coverage }}</span>
                  <span class="coverage-amount">/{{ plan.termMonths }}ヶ月</span>
                </div>
              </div>

              <el-button 
                type="primary" 
                class="select-plan-btn" 
                @click="selectPlan(plan)">
                このプランを選択する
              </el-button>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
      <el-dialog 
        v-model="editPetDialogVisible" 
        title="ペット情報を編集する" 
        width="500px"
      >
        <el-form :model="editingPet" label-width="100px" class="pet-form">
          <el-form-item label="名前">
            <el-input v-model="editingPet.petName" placeholder="ペット名を入力してください"></el-input>
          </el-form-item>
          <el-form-item label="品種">
            <el-input v-model="editingPet.breed" placeholder="品種を入力してください"></el-input>
          </el-form-item>
          <el-form-item label="種類">
            <el-input v-model="editingPet.species" placeholder="種類を入力してください"></el-input>
          </el-form-item>
          <el-form-item label="年齢">
            <el-input-number 
              v-model="editingPet.age" 
              :min="0" 
              :max="20"
              controls-position="right">
            </el-input-number>
          </el-form-item>
          <el-form-item label="性別">
            <el-select v-model="editingPet.gender" placeholder="性別を選択してください">
              <el-option label="雄性" value="Male"></el-option>
              <el-option label="雌性" value="Female"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="editPetDialogVisible = false">キャンセル</el-button>
            <el-button type="primary" @click="updatePet">保存</el-button>
          </span>
        </template>
      </el-dialog>

      <!-- Order Details Dialog -->
      <el-dialog
      v-model="checkInsuranceDetailDialog"
       
        title="保険詳細"
        width="500px"
      >
        <div v-if="selectedPetPlan" class="order-details">
          <div class="detail-item">
              <span class="label">注文番号：</span>
              <span>{{ selectedPetPlan.orderId }}</span>
            </div>
            <div class="detail-item">
              <span class="label">管理者番号：</span>
              <span>{{ selectedPetPlan.vendorId }}</span>
            </div>
          
            <div class="detail-item">
              <span class="label">保険プラン：</span>
              <span>{{ selectedPetPlan.policyName }}</span>
            </div>
      
            <div class="detail-item">
              <span class="label">状態：</span>
              <el-tag :type="getOrderStatusType(selectedPetPlan.orderStatus)">
                {{ getOrderStatusText(selectedPetPlan.orderStatus) }}
              </el-tag>
            </div>
            <div class="detail-item">
              <span class="label">注文日：</span>
              <span>{{ selectedPetPlan.startDate }}</span>
            </div>
            <div class="detail-item">
              <span class="label">有効期限：</span>
              <span>{{ selectedPetPlan.endDate }}</span>
            </div>
            <div class="detail-item">
              <span class="label">金額：</span>
              <span>¥{{ selectedPetPlan.price }}</span>
            </div>
            <div class="detail-item">
              <span class="label">補償額：</span>
              <span>¥{{ selectedPetPlan.coverage }}</span>
            </div>
        </div>
      </el-dialog>
      <el-dialog
      v-model="orderDetailsDialogVisible"
       
        title="注文詳細"
        width="500px">
        <div  class="order-details">
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
      <!-- 添加宠物对话框 -->
      <el-dialog 
        v-model="addPetDialogVisible" 
        title="ペットを追加する" 
        custom-class="pet-dialog"
        width="500px"
      >
        <el-form :model="newPet" label-width="100px" class="pet-form">
          <el-form-item label="名前">
            <el-input v-model="newPet.name" placeholder="请输入宠物名称"></el-input>
          </el-form-item>
          <el-form-item label="品種">
            <el-input v-model="newPet.breed" placeholder="请输入品种"></el-input>
          </el-form-item>
          <el-form-item label="種類">
            <el-input v-model="newPet.species" placeholder="请输入种类"></el-input>
          </el-form-item>
          <el-form-item label="年齢">
            <el-input-number 
              v-model="newPet.age" 
              :min="0" 
              :max="20"
              controls-position="right">
            </el-input-number>
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="newPet.gender" placeholder="性别を選びなさい">
              <el-option label="雄性" value="1"></el-option>
              <el-option label="雌性" value="2"></el-option>
            </el-select>
          </el-form-item>
        
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="addPetDialogVisible = false">キャンセル</el-button>
            <el-button type="primary" @click="addPet">確認</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, reactive, onMounted, watch } from 'vue';
import { ElMessage, ElMessageBox, ElTable, ElTableColumn, ElDialog, ElInput, ElButton, ElSelect } from 'element-plus';

export default {
  components: {
    ElTable,
    ElTableColumn,
    ElDialog,
    ElInput,
    ElButton,
    ElSelect
  },
  name: 'PetInsuranceSystem',
  setup() {
    const title = ref('ペット保険システム');
    const activeTab = ref('pets');
    const addPetDialogVisible = ref(false);
    const pets = ref([]);
    const insurancePlans = ref([]);
    const editPetDialogVisible = ref(false);
    const orderDetailsDialogVisible = ref(false);
    const editingPet = ref({});
    const selectedOrder = ref(null);
    const checkInsuranceDetailDialog=ref(false);
    const selectedPetPlan = ref(null);
    

    const newPet = reactive({
      name: '',
      species: '',
      breed: '',
      age: 1,
      gender: '',
      insuranceStatus:''
    });

   
   

    // Orders pagination
    const orders = ref([]);
    const orderPage = ref(1);
    const orderPageSize = ref(10);
    const orderTotal = ref(0);

    // Fetch orders
    const fetchOrders = async () => {
      try {
        const token = localStorage.getItem('token') || sessionStorage.getItem('token');
        const response = await axios.get(`http://localhost:8081/orders`, {
          params: {
            page: orderPage.value,
            size: orderPageSize.value
          },
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        orders.value = response.data.data.records;
        console.log(orders.value);
        orderTotal.value = response.data.data.total;
      } catch (error) {
        // ElMessage.error('注文履歴の取得に失敗しました');
      }
    };

    // Handle order page change
    const handleOrderPageChange = (page) => {
      orderPage.value = page;
      fetchOrders();
    };

    // Show order details
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
        orderDetailsDialogVisible.value = true;
      } catch (error) {
        // ElMessage.error('注文詳細の取得に失敗しました');
      }
    };

    // Show edit pet dialog
    const showEditPetDialog = (pet) => {
      editingPet.value = { ...pet };
      editPetDialogVisible.value = true;
    };

    // Update pet
    const updatePet = async () => {
      try {
        const token = localStorage.getItem('token') || sessionStorage.getItem('token');
        await axios.put('http://localhost:8081/pets', editingPet.value, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        ElMessage.success('ペット情報の更新に成功しました');
        editPetDialogVisible.value = false;
        fetchPets();
      } catch (error) {
        ElMessage.error('ペット情報の更新に失敗しました');
      }
    };

    // Confirm and delete pet
    const confirmDeletePet = (pet) => {
      ElMessageBox.confirm(
        'このペットを削除してもよろしいですか？',
        '確認',
        {
          confirmButtonText: '削除',
          cancelButtonText: 'キャンセル',
          type: 'warning',
        }
      )
        .then(() => {
          deletePet(pet.petId);
        })
        .catch(() => {
          // User canceled
        });
    };

    const checkInsuranceDetail=async(pet)=>{
      try {
        
        const token = localStorage.getItem('token') || sessionStorage.getItem('token');
        const response=await axios.get(`http://localhost:8081/orders/pet/${pet.petId}`, {
          headers: {
            Authorization: `${token}`
          }
        });
        checkInsuranceDetailDialog.value=true;
        selectedPetPlan.value=response.data.data;
       
      } catch (error) {
        console.log(error);
       
      }
    };
    

    // Delete pet
    const deletePet = async (petId) => {
      try {
        const token = localStorage.getItem('token') || sessionStorage.getItem('token');
        await axios.delete(`http://localhost:8081/pets/${petId}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        ElMessage.success('ペットの削除に成功しました');
        fetchPets();
      } catch (error) {
        ElMessage.error('ペットの削除に失敗しました');
      }
    };

    // Order status helpers
    const getOrderStatusType = (status) => {
      const statusMap = {
        'Pending': 'warning',
        'Active': 'success',
        'Cancelled': 'danger'
      };
      return statusMap[status] || 'info';
    };

    const getOrderStatusText = (status) => {
      const statusMap = {
        'Pending': '処理中',
        'Active': '完了',
        'Cancelled': 'キャンセル',
        'Expired':'过期'
      };
      return statusMap[status] || status;
    };

    // Watch for tab changes
    watch(activeTab, (newTab) => {
      if (newTab === 'pets') {
        fetchPets();
      } else if (newTab === 'orders') {
        fetchOrders();
      }
    });

    onMounted(() => {
      fetchPets();
      fetchInsurancePlans();
      fetchOrders();
    });


    watch(activeTab, (newTab) => {
      if (newTab === 'pets') {
        fetchPets(); // 当 tab 切换到 "私のペット" 时执行 fetchPets
      }
    });

    const fetchPets = async () => {
      try {
        
        const token = localStorage.getItem('token') || sessionStorage.getItem('token');
        const response = await axios.get('http://localhost:8081/pets/list?page=1&size=100', {
          
          headers: {
            Authorization: `Bearer ${token}`,
            withCredentials: true
          }
        });
        
        pets.value = response.data.data.records;
        console.log(pets.value);
      } catch (error) {
        // ElMessage.error('ペットデータの取得に失敗しました');
        console.log(error);
      }

      sessionStorage.removeItem('selectedPet');
    };

    const fetchInsurancePlans = async () => {
      try {
        const token = localStorage.getItem('token') || sessionStorage.getItem('token');
        const response = await axios.get('http://localhost:8081/policy', {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        insurancePlans.value = response.data.data;
        console.log(insurancePlans.value);
      } catch (error) {
        // ElMessage.error('保険プランの取得に失敗しました');
      }
    };

    const showAddPetDialog = () => {
      addPetDialogVisible.value = true;
    };

    const addPet = async () => {
      try {
        const token = localStorage.getItem('token') || sessionStorage.getItem('token');
        if (!token) {
          // ElMessage.error('ユーザートークンが見つかりません');
          return;
        }

        const response = await axios.post('http://localhost:8081/pets', {
          petName: newPet.name,
          species: newPet.species,
          breed: newPet.breed,
          age: newPet.age,
          gender: newPet.gender,
          insuranceStatus:newPet.insuranceStatus
        }, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        pets.value.push(response.data);
        addPetDialogVisible.value = false;

        newPet.name = '';
        newPet.species = '';
        newPet.breed = '';
        newPet.age = 1;
        newPet.gender = '';
        newPet.insuranceStatus='';

        ElMessage.success('ペットの追加に成功しました');
        fetchPets();
      } catch (error) {
        console.log(error);
      }
    };

    const selectPlan = async (plan) => {
  try {
    // 从 sessionStorage 获取当前选择的宠物信息
    const selectedPet = JSON.parse(sessionStorage.getItem('selectedPet'));
    console.log(selectedPet);

    if (!selectedPet || !selectedPet.petId) {
      ElMessage.error('まず保険に加入しているペットを選んでください');
      activeTab.value = 'pets';
      return;
    }

    // 构造订单对象
    const order = {
      petId: selectedPet.petId,         // 宠物 ID
      policyId: plan.policyId,       // 保険プラン ID
      orderStatus: 'Pending',        // 初始订单状态
    };
console.log(order);
    // 获取用户 token
    const token = localStorage.getItem('token') || sessionStorage.getItem('token');
    console.log(token);
    if (!token) {
      // ElMessage.error('ユーザートークンが見つかりません');
      return;
    }

    // 调用后端接口创建订单
    await axios.post('http://localhost:8081/orders', order, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    
    // 提示订单创建成功
    ElMessage.success(`${plan.policyName}を選択しました。注文が作成されました！`);
    activeTab.value = 'pets';
    fetchPets();
  } catch (error) {
    console.error('注文作成中にエラーが発生しました', error);
    ElMessage.error('注文作成に失敗しました');
  }
};

    const buyInsurance=(pet)=>{
        // 获取宠物的ID
        // const petId = pet.id;

       // 将宠物信息存储到 sessionStorage（或用于其他用途）
        sessionStorage.setItem('selectedPet', JSON.stringify(pet));

        activeTab.value = 'plans';
    }

   
    return {
      title,
      activeTab,
      pets,
      insurancePlans,
      addPetDialogVisible,
      newPet,
      showAddPetDialog,
      addPet,
      selectPlan,
      buyInsurance,
      orders,
      orderPage,
      orderPageSize,
      orderTotal,
      editPetDialogVisible,
      orderDetailsDialogVisible,
      editingPet,
      selectedOrder,
      showEditPetDialog,
      updatePet,
      confirmDeletePet,
      handleOrderPageChange,
      showOrderDetails,
      getOrderStatusType,
      getOrderStatusText,
      checkInsuranceDetail,
      selectedPetPlan,
      checkInsuranceDetailDialog
    };
  }
};
</script>

<style>
body {
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
  background: #f5f7fa;
  color: #2c3e50;
}

.header {
  background: linear-gradient(120deg, #3498db, #2980b9);
  padding: 1.5rem 0;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
}

.header h1 {
  color: white;
  margin: 0;
  font-size: 1.8rem;
  font-weight: 500;
}

.container {
  max-width: 1200px;
  margin: 2rem auto;
  padding: 0 2rem;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.section-title {
  font-size: 1.5rem;
  color: #2c3e50;
  margin: 0;
  font-weight: 500;
}

.pet-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
  margin-top: 1.5rem;
}

.pet-card {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.pet-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.pet-card-header {
  display: flex;
  align-items: center;
  margin-bottom: 1.5rem;
}

.pet-avatar {
  width: 48px;
  height: 48px;
  background: #e1f5fe;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 1rem;
}

.pet-name {
  margin: 0;
  font-size: 1.25rem;
  color: #2c3e50;
}

.pet-info {
  margin-bottom: 1.5rem;
}

.info-item {
  display: flex;
  margin-bottom: 0.5rem;
}

.label {
  color: #666;
  width: 60px;
}

.value {
  color: #2c3e50;
  font-weight: 500;
}

.insurance-status {
  margin-bottom: 1.5rem;
  padding: 1rem;
  background: #f8f9fa;
  border-radius: 8px;
}

.status-tag {
  display: inline-block;
  padding: 0.25rem 0.75rem;
  border-radius: 4px;
  font-size: 0.875rem;
  font-weight: 500;
}

.status-tag.active {
  background: #e1f5fe;
  color: #0288d1;
}

.status-tag.inactive {
  background: #ffebee;
  color: #e53935;
}

.expiry-date {
  margin: 0.5rem 0 0;
  font-size: 0.875rem;
  color: #666;
}

.plans-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
  margin-top: 1.5rem;
}

.plan-card {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
}

.plan-header {
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #eee;
}

.plan-header h3 {
  margin: 0 0 1rem;
  color: #2c3e50;
}

.premium {
  font-size: 1.5rem;
  color: #e53935;
  font-weight: 600;
}

.premium span {
  font-size: 1rem;
  color: #666;
  font-weight: normal;
}

.plan-content {
  flex-grow: 1;
  margin-bottom: 1.5rem;
}

.description {
  margin: 0 0 1rem;
  color: #666;
  line-height: 1.5;
}

.coverage {
  background: #f8f9fa;
  padding: 1rem;
  border-radius: 8px;
}

.coverage-label {
  display: block;
  color: #666;
  margin-bottom: 0.5rem;
}

.coverage-amount {
  font-size: 1.25rem;
  color: #2c3e50;
  font-weight: 500;
}

.orders-table {
  background: white;
  border-radius: 8px;
  padding: 1.5rem;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.pagination {
  margin-top: 1rem;
  display: flex;
  justify-content: center;
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

.detail-label {
  width: 120px;
  color: #666;
  font-weight: 500;
}

.detail-value {
  color: #2c3e50;
}
  </style>