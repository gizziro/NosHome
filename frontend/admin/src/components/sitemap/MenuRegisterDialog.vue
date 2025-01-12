<template>
  <div class="w-[300px] lg:w-[400px]">

    <div class="input-low flex flex-col lg:flex-row">
      <div class="input-label flex text-xs lg:text-md items-center mb-1 lg:mb-0">메뉴 명</div>
      <div class="input-box">
        <InputText type="text"
                   ref="menuNameInput"
                   v-model="menuName"
                   size="small"
                   class="text-xs w-full md:w-56" />
      </div>
    </div>

    <div class="input-low flex flex-col lg:flex-row">
      <div class="input-label flex text-xs lg:text-md items-center mb-1 lg:mb-0">url slug</div>
      <div class="input-box">
        <InputText type="text"
                   ref="menuSlugInput"
                   v-model="menuSlug"
                   size="small"
                   class="text-xs w-full md:w-56" />
      </div>
    </div>

    <div class="input-low flex flex-col lg:flex-row">
      <div class="input-label flex text-xs lg:text-md items-center mb-1 lg:mb-0">메뉴 유형</div>
      <div class="input-box">
        <Select v-model="selectedModule"
                ref="moduleSelect"
                :options="serviceModuleList"
                optionLabel="name"
                placeholder="메뉴 유형 선택"
                class="text-xs w-full md:w-56" />
      </div>
    </div>
    <div class="input-low flex flex-col lg:flex-row">
      <div class="input-label flex text-xs lg:text-md items-center mb-1 lg:mb-0">메뉴 유형</div>
      <div class="input-box">
        <Textarea v-model="menuDescription"
                  ref="menuDescriptionInput"
                  rows="5" cols="30" class="text-xs w-full md:w-56" />
      </div>
    </div>

    <div class="flex justify-end gap-2">
      <Button type="button" label="취소" severity="secondary" @click="onClickCancel"></Button>
      <Button type="button" label="추가" @click="onClickAdd"></Button>
    </div>
  </div>
</template>

<script setup>
import {useCommonStore} from "@/stores/commonStore.js";
import { ref }          from "vue";
import { storeToRefs }  from "pinia";
import Select           from 'primevue/select';
import InputText        from 'primevue/inputtext';
import Button           from "primevue/button";
import Textarea         from 'primevue/textarea';

const commonStore = useCommonStore();
const { serviceModuleList } = storeToRefs(commonStore);




//----------------------------------------------------------------------------------------------------------------------
// UI 컴포넌트 입력 처리 관련
//----------------------------------------------------------------------------------------------------------------------
const menuNameInput         = ref(null);
const menuName              = ref('');
const menuSlugInput         = ref(null);
const menuSlug              = ref('');
const moduleSelect          = ref(null);
const selectedModule        = ref(null);
const menuDescriptionInput  = ref(null);
const menuDescription       = ref('');



const emit = defineEmits(['onCancel', 'onRegister'])

const onClickCancel = () => {
  console.log('onClickCancel');
  emit('onCancel', false);
}
const onClickAdd = () => {

  if (menuName.value === '') {
    menuNameInput.value.$el.focus(); // InputText의 경우
    return;
  }

  if (menuSlug.value === '') {
    menuSlugInput.value.$el.focus();
    return;
  }

  if (!selectedModule.value) {
    moduleSelect.value.$el.focus();
    return;
  }
  if (menuDescription.value === '') {
    menuDescriptionInput.value.$el.focus();
    return;
  }

  emit('onRegister', {name : menuName.value, slug : menuSlug.value, moduleId : selectedModule.value.moduleId, description : menuDescription.value });
}

</script>



<style scoped>

.input-low
{
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
}

.p-select:not(.p-disabled).p-focus
{
  border-color: #2b3348;
}

.p-textarea
{
  font-size : 14px;
}

</style>